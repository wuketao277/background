部署路径：/opt 前台、后台、nginx、上传的文件都在这个目录下
部署：sftp root@IP地址   回车后输入密码(L开头+1)
上传后台应用：put -r /Users/wuketao/Public/study/github/hello/background/target/background-1.0-SNAPSHOT.jar /opt/background/
启动后台应用：./start.sh

前端项目打包：rm -rf dist      npm run build
上传前台应用：put -r /Users/wuketao/Public/study/github/hello/front/hello/dist/* /opt/front/
最后要重启nginx：/usr/sbin/nginx -s reload
nginx的配置文件：/etc/nginx/nginx.conf
浏览器删除缓存

start.sh nohup java -jar -Dspring.profiles.active=prd background-1.0-SNAPSHOT.jar