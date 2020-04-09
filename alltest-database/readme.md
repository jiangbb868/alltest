创建2个用户，写入数据库
-user -c 2 -h localhost -p 5432 -d itone -u dbadmin -s dbadmin
创建2个用户，写入文件
-user -c 2 -f init_user.sql

创建100个uuid，写入文件
-uuid -c 100 -f uuid.csv