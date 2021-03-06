
# Linux基本命令

命令格式： 命令 [选项] [参数]  
**不懂的时候问男人！！！ Linux命令的详细选项和参数都有提示**

```bash
man ls
```

命令界面内容解释

```bash
[root@localhost ~]#
```

* root: 当前登录用户
* localhost: 当前计算机名
* ~: 当前目录(root用户>/root,普通用户user1>/home/user1)
* #: 表示当前账号是管理员 $: 表示普通用户

## 1.目录命令

### 查询当前目录的绝对路径

``` bash
pwd
```

### 查询目录中的文件

``` bash
ls [目录名]
ll 等于 ls -l
```

文件类型(-l可以展示)，分为4部分，对应的权限是： r读 w写 x执行

* 第1个字母 文件类型 (-文件 d目录文件 l软链接 etc..)
* 第2~4字母 u所有者
* 第5~7字母 g所属组
* 第8~10字母 o其他人

比如：-rw-r--r-- 表示为文件，所有者可读写，所属组可读，其他人可读

### 建立目录

``` bash
mkdir [目录名]
mkdir -p 递归创建 比如：mkdir -p a/b/c
```

### 切换目录

``` bash
cd [目录名]
cd ~ #进入当前用户的home目录
cd - #进入上次的目录
cd .. #进入上一级目录
cd . #进入当前目录
```

### 删除目录

``` bash
rmdir #删除空目录
rm -rf #强制递归的删除目录或文件！！真删除，难恢复
rm -rf / #自杀命令，请勿执行!请勿执行!请勿执行!
```

### 复制目录

``` bash
cp [源文件] [目标文件]
cp -r #递归复制目录
cp -p #连带文件属性一起复制
cp -d #若文件是链接文件，则复制链接属性
cp -a #可保留文件所有的原始信息
```

### 剪切或改名目录

``` bash
mv [源文件] [目标文件]
```

### 链接

``` bash
ln [源文件] [目标文件]
ln -s [源文件] [目标文件] #创建软链接 源文件必须为绝对路径
```

删除软链接时，不能在最后加'/'会把原始文件夹内容一起删除。

``` bash
rm -rf  ./test_chk_ln #删除软链接，但不删除实际数据
rm -rf  ./test_chk_ln/ #删除软链接，并删除实际数据
```

### 常用目录

路径|名称|作用
---|:---|:---
/ | 根目录 |
/bin -> /usr/bin | 命令保存目录 | 普通用户就可以读取的命令
/sbin -> /usr/sbin | 命令保存目录 | 仅超级管理员读取的命令
/boot | 启动目录 | 启动相关文件
/dev | 设备文件保存目录 |
/etc | 配置文件保存目录 |
/home | 普通用户的home目录 |
/root | 超级用户的home目录 |
/lib | 函数库保存目录 |
/mnt | 系统挂载目录 |
/media | 挂载目录 |
/tmp | 临时目录 |
/proc | 虚拟文件系统目录 | 是系统内存的映射不能直接操作
/usr | 系统软件资源目录 |
/var | 系统相关文档内容 | 用于存放运行时需要改变数据的文件，比如日志

## 2.文件命令

### 文件搜索

#### locate

效率快很多，原因在于它不搜索具体目录，而是搜索一个数据库（/var/lib/mlocate），这个数据库中含有本地所有文件信息。Linux系统自动创建这个数据库，并且每天自动更新一次，所以使用locate命令查不到最新变动过的文件。  
为了避免这种情况，可以在使用locate之前，先使用updatedb命令，手动更新数据库。  
locate只能搜索文件名

``` bash
locate [查询的文件]
yum install -y mlocate #如果没有的情况，要这个命令安装
updatedb
locate inittab
```

#### whereis / which

搜索命令的命令,只能查询系统命令

``` bash
whereis ls
-b 只看命令所在目录
-m 只看帮助文档所在目录

which ll #可以看见命令别名
```

#### find

非常强大功能众多，所以选项会比较多  
避免大范围的搜索，会非常耗费系统资源  
在系统当中搜索符合条件的文件名，如果需要匹配，使用通配符匹配，通配符是完全匹配。

通配符|作用
---|:---
* | 匹配任意内容
? | 匹配任意一个字符
[] | 匹配任意一个中括号内的字符

``` bash
find [搜索范围] [搜索条件]
-iname 不区分大小写
-user 按照所有者搜索
find ~ -name anaconda-ks.cfg
find ~ -name "*.cfg"

-atime 文件访问时间
-ccime 改变文件属性
-mtime 修改文件内容
find /var/log -mtime +10 #查询10天前修改的文件 -10表示10天内 10表示10天当天

-size 文件大小
find . -size 25k #查询文件大小是25KB的文件 -25表示小于25KB +25表示大于25KB

-i i节点

-a and 逻辑与
-o or 逻辑或

-exec 命令 {} \; 结果给第二条命令处理
find /etc/ -size +20k -a -size -50k -exec ls -lh {} \; #查询/etc目录下面文件大小在20~50KB之间的文件，并利用ls显示详细信息
```

#### grep

搜索字符串命令，模糊匹配
在文件当中搜索符合条件的字符串，如果需要匹配，使用正则表达式进行匹配，正则表达式是包含匹配。

``` bash
grep [选项] [字符串] [文件名]
-i 忽略大小写
-v 排除指定字符串
grep -i "network" anaconda-ks.cfg
```

## 3.帮助命令

### man

获取指定命令的帮助文档

``` bash
man -k [命令] #查询命令相关的所有帮助
apropos #类似man -k
```

### whatis

查询一个命令执行什么功能，并将查询结果打印到终端上

``` bash
whatis [命令] #类似man -f
```

### help

shell内部命令帮助

``` bash
[命令] --help #ls --help
help [命令] #help cd
```

## 4.压缩命令

### zip

与windows中zip的一致的，可以相互传递使用

``` bash
zip 压缩文件名 源文件
zip test.zip test

zip -r 压缩文件名 源目录
zip -r test.zip test

unzip 压缩文件
unzip test.zip
```

### gzip

``` bash
gzip 源文件 #源文件会消失
gzip test

zip -r 源目录 #不是目录会报错，压缩目录下的所有文件，但不能压缩目录
zip -r test

gunzip 压缩文件
gunzip test.gz
```

### bzip2

不能压缩目录

``` bash
bzip2 源文件 #源文件会消失
bzip2 test

bzip2 -k 源文件 #保留原文件
bzip2 -k test

bunzip2 压缩文件
bunzip2 test.gz
```

### tar

其实.tar.gz格式是先打包为.tar格式，在压缩为.gz格式，可以同时打包压缩多个目录

``` bash
tar -cvf 打包文件名 源文件 源文件 #c:打包 v:显示过程 f:指定打包后的文件名
tar -cvf test.tar test

tar -xvf 打包文件名 #x:解打包
tar -xvf test.tar

#.gz
tar -zcvf 压缩包 源文件 源文件 #z:压缩为.gz
tar -zcvf test.tar.gz test

tar -zxvf 压缩包
tar -zxvf test.tar.gz

#.bz2
tar -jcvf 压缩包 源文件 源文件 #j:压缩为.bz2
tar -jcvf test.tar.bz2 test

tar -jxvf 压缩包
tar -jxvf test.tar.bz2

#指定解压目录
tar -zxvf test.tar.gz -C /tmp/

#查看压缩包
tar -ztvf test.tar.gz
```

## 5.关机重启命令

``` bash
shutdown [选项] 时间
-c 取消前一个关机命令
-h 关机
-r 重启

#马上重启
shutdown -r now
```
