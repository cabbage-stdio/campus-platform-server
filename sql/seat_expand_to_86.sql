-- 在项目数据库 campus_platform 中执行。
-- 默认补充 LIB 一楼 A051～A086；其他楼层请修改下面两个参数后执行。
-- 不修改已有座位；同时同步自动编号序列。执行期间遇到错误请 ROLLBACK。
SET @seat_area_code = 'LIB';
SET @seat_floor_no = 1;
SET @seat_prefix = CONCAT(@seat_area_code, '-F', @seat_floor_no);

-- 从已有座位确定区域 ID，不假定区域 ID 为 1。
-- 只有唯一匹配区域时才插入，未找到或匹配多个区域时不会写入。
SET @seat_area_id = (
    SELECT CASE WHEN COUNT(DISTINCT s.area_id) = 1 THEN MIN(s.area_id) END
    FROM seat s
    JOIN campus_area a ON a.id = s.area_id
    WHERE a.area_code = @seat_area_code
      AND s.floor_no = @seat_floor_no
      AND LEFT(s.seat_code, CHAR_LENGTH(@seat_prefix) + 2)
          = CONCAT(@seat_prefix, '-A')
);

SELECT @seat_prefix AS target_prefix, @seat_area_id AS target_area_id,
       CASE WHEN @seat_area_id IS NULL
            THEN 'STOP: no unique existing area/floor; check the parameters'
            ELSE 'Ready: insert missing A051-A086'
       END AS result;

START TRANSACTION;

-- 与应用新增座位使用相同的序列行锁，编号至少推进到 86，且不会倒退。
INSERT INTO seat_code_sequence (code_prefix, current_no)
SELECT @seat_prefix, GREATEST(86, COALESCE(MAX(
    CAST(SUBSTRING(s.seat_code, CHAR_LENGTH(@seat_prefix) + 3) AS UNSIGNED)
), 0))
FROM seat s
WHERE s.area_id = @seat_area_id
  AND s.floor_no = @seat_floor_no
  AND LEFT(s.seat_code, CHAR_LENGTH(@seat_prefix) + 2)
      = CONCAT(@seat_prefix, '-A')
  AND SUBSTRING(s.seat_code, CHAR_LENGTH(@seat_prefix) + 3) REGEXP '^[0-9]+$'
HAVING @seat_area_id IS NOT NULL
ON DUPLICATE KEY UPDATE current_no = GREATEST(current_no, VALUES(current_no));

INSERT INTO seat (
    area_id, floor_no, seat_code, location_detail, status,
    remark, create_by, create_time, update_by, update_time
)
SELECT @seat_area_id, @seat_floor_no,
       CONCAT(@seat_prefix, '-A', LPAD(numbers.seat_no, 3, '0')),
       CASE
           WHEN numbers.seat_no BETWEEN 51 AND 62 THEN '桌组四'
           WHEN numbers.seat_no BETWEEN 63 AND 74 THEN '桌组五'
           ELSE '桌组六'
       END,
       '0', '扩充楼层座位至86个', 'admin', NOW(), 'admin', NOW()
FROM (
    SELECT tens.n + ones.n AS seat_no
    FROM (SELECT 50 AS n UNION ALL SELECT 60 UNION ALL SELECT 70 UNION ALL SELECT 80) tens
    CROSS JOIN (
        SELECT 0 AS n UNION ALL SELECT 1 UNION ALL SELECT 2
        UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5
        UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9
    ) ones
) numbers
WHERE numbers.seat_no BETWEEN 51 AND 86
  AND @seat_area_id IS NOT NULL
  AND NOT EXISTS (
      SELECT 1 FROM seat existing
      WHERE existing.seat_code = CONCAT(@seat_prefix, '-A', LPAD(numbers.seat_no, 3, '0'))
  )
ORDER BY numbers.seat_no;

SELECT ROW_COUNT() AS inserted_seat_count;
COMMIT;

-- 原有 A001～A050 完整时，此次应新增 36 条，共 86 条。
SELECT area_id, floor_no, COUNT(*) AS total_seat_count
FROM seat
WHERE area_id = @seat_area_id AND floor_no = @seat_floor_no
GROUP BY area_id, floor_no;

SELECT code_prefix, current_no
FROM seat_code_sequence
WHERE code_prefix = @seat_prefix;
