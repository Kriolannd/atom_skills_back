INSERT INTO role(id, name) VALUES (nextval('role_seq'),'ROLE_ADMIN');
INSERT INTO app_user (id,password,username) VALUES (nextval('app_user_seq'),'$2a$10$kKYv3qlcXVAY8S8t1FiMsOkm6DBsoIN8aXv9AlsjxqJ.GgVuQDxMi','admin');
INSERT INTO app_user_roles (app_user_id,roles_id) VALUES (currval('app_user_seq'),currval('role_seq'));

INSERT INTO some_entity (id,"number",some_enum,"date",file,"text") VALUES (1,101,0,'2024-01-01',NULL,'text 1');
INSERT INTO some_entity (id,"number",some_enum,"date",file,"text") VALUES (2,102,0,'2024-01-02',NULL,'text 2');
INSERT INTO some_entity (id,"number",some_enum,"date",file,"text") VALUES (3,103,0,'2024-01-03',NULL,'text 3');
INSERT INTO some_entity (id,"number",some_enum,"date",file,"text") VALUES (4,104,0,'2024-01-04',NULL,'text 4');
INSERT INTO some_entity (id,"number",some_enum,"date",file,"text") VALUES (5,105,0,'2024-01-05',NULL,'text 5');
INSERT INTO some_entity (id,"number",some_enum,"date",file,"text") VALUES (6,106,1,'2024-01-06',NULL,'text 6');
INSERT INTO some_entity (id,"number",some_enum,"date",file,"text") VALUES (7,107,1,'2024-01-07',NULL,'text 7');
INSERT INTO some_entity (id,"number",some_enum,"date",file,"text") VALUES (8,108,1,'2024-01-08',NULL,'text 8');
INSERT INTO some_entity (id,"number",some_enum,"date",file,"text") VALUES (9,109,1,'2024-01-09',NULL,'text 9');
INSERT INTO some_entity (id,"number",some_enum,"date",file,"text") VALUES (10,110,1,'2024-01-10',NULL,'text 10');

SELECT setval('some_entity_seq', 10);