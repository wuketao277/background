部署路径：/opt 前台、后台、nginx、上传的文件都在这个目录下
部署：sftp root@IP地址   回车后输入密码
上传后台应用：put /Users/wuketao/Public/study/github/hello/background/target/background-1.0-SNAPSHOT.jar /opt/background/
启动后台应用：./start.sh
