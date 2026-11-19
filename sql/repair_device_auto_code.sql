-- 新增设备时先取得自增 ID，再在同一事务中写入设备编号。
-- 已存在的设备编号保持原样，唯一索引 uk_device_code 保留。
ALTER TABLE repair_device
    MODIFY COLUMN device_code VARCHAR(50) NULL COMMENT '设备编号';
