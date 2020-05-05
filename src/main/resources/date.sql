--Sorry for renaming this file, but I don't know how else to turn this off, I've tried in properties
INSERT INTO `Role` (`name`) VALUES ('ROLE_USER'), ('ROLE_ADMIN');
INSERT INTO `Privilege` (`name`) VALUES ('USE_WAREHOUSE'), ('MODIFY_WAREHOUSE');
INSERT INTO `Person` (`username`, `first_name`, `last_name`, `e_mail`, `password`, `display_picture`)
    VALUES ('ADMIN', 'ADMIN', '', '', '12345678', null);
INSERT INTO `Person` (`username`, `first_name`, `last_name`, `e_mail`, `password`, `display_picture`)
    VALUES ('DavidAce', 'David', '', 'david@example.com', 'lovely_puppy', null);
INSERT INTO `Warehouse` (`name`, `address`) VALUES ('The Orange Warehouse', '123 Ajah Road');
INSERT INTO `Warehouse_Users` (`user_id`, `warehouse_id`) VALUES ((SELECT `id` FROM `Person` WHERE `username` = 'DavidAce'), (SELECT `id` FROM `Warehouse` WHERE `name` = 'The Orange Warehouse'));
INSERT INTO `Role_Person` (`person_id`, `role_id`) VALUES ((SELECT `id` FROM `Person` WHERE `username` = 'ADMIN'), (SELECT `id` FROM `Role` WHERE `name` = 'ROLE_USER'));
INSERT INTO `Role_Person` (`person_id`, `role_id`) VALUES ((SELECT `id` FROM `Person` WHERE `username` = 'ADMIN'), (SELECT `id` FROM `Role` WHERE `name` = 'ROLE_ADMIN'));
INSERT INTO `Role_Person` (`person_id`, `role_id`) VALUES ((SELECT `id` FROM `Person` WHERE `username` = 'DavidAce'), (SELECT `id` FROM `Role` WHERE `name` = 'ROLE_USER'));