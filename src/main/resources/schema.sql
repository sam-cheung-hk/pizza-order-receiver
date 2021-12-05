CREATE TABLE IF NOT EXISTS `pizza_order` (
    `id` varchar(64) NOT NULL,
    `order_ref_no` varchar(64) NOT NULL,
    `order_time` datetime NOT NULL,
    `total_price` decimal(11, 2) NOT NULL,
    `creation_time` datetime NOT NULL,
    `modification_time` datetime NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `pizza_order_detail` (
    `id` varchar(64) NOT NULL,
    `pizza_order_id` varchar(64) NOT NULL,
    `name` varchar(100) NOT NULL,
    `quantity` int(11) NOT NULL,
    `price` decimal(11, 2) NOT NULL,
    `creation_time` datetime NOT NULL,
    `modification_time` datetime NOT NULL,
    PRIMARY KEY (`id`)
);
