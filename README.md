# Benkyoukai



## [图片预览/プレビュー/preview](#preview)

## [中文](#中文说明)

## [日本語](#日本語)

## [English(Machine Translation)](#English)





## preview

![20240822212614.png](https://s2.loli.net/2024/08/22/UJO4MNwHQjRzETG.png)

![20240822212707.png](https://s2.loli.net/2024/08/22/kjPfFoGKWIJ5N9S.png)

![20240822212839.png](https://s2.loli.net/2024/08/22/2ZFXdHi8tNcRqeo.png)

![20240822212820.png](https://s2.loli.net/2024/08/22/Mu5I9X3LHPA2din.png)

![20240822213200.png](https://s2.loli.net/2024/08/22/iJUvQOtL9aBnlXN.png)



----



## 中文说明

**Benkyoukai**

基于SpringBoot+Vue的日语学习博客与会话练习Web项目,内置中文、日语、英语页面三语言切换功能



### 运行步骤

#### 目录确认

- Benkyoukai_Java	Web项目的后端

- Benkyoukai_vue	Web项目的前端

- Design					最初设计草稿

- kaiwa						存放会话音频的文件夹

- SQL						数据库的sql文件

- uploads					图片存放的文件夹



### 后端部署方法

1.拉取项目

```powershell
git clone git@github.com:c59z/Benkyoukai.git
```

2.创建Mysql数据库以及导入sql文件

```sql
create database benkyoukai charset=utf8;

use benkyoukai;

source benkyoukai.sql
```

3.安装maven依赖

```powershell
mvn clean install
```

4.请修改一下内容:

application.yml

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/benkyoukai?characterEncoding=utf-8&serverTimezone=Asia/Shanghai
    username: username # 这里输入您的mysql用户名
    password: password # mysql密码
    driver-class-name: com.mysql.cj.jdbc.Driver
  servlet:
    multipart:
      max-file-size: 2MB
      max-request-size: 5MB
      
      
file-save-path: D:\code\Project\SotsuGyou\uploads\   # 您拉取到的upload文件夹的绝对路径

avatar-path: D:\code\Project\SotsuGyou\uploads\avatar\ # avatar用户头像文件夹的绝对路径

kaiwa-path: D:\code\Project\SotsuGyou\kaiwa\ # 会话文件夹的绝对路径
 
```

5.启动项目

#### 前端部署方法

注意：请在后端正常运行的情况下启动前端项目

1.安装依赖

```powershell
npm install
```

2.运行

```powershell
npm run dev
```



----



## 日本語

**Benkyoukai**

SpringBootとVueを使用した日本語学習ブログと会話練習Webプロジェクト,中国語、日本語、英語の3言語切り替え機能あり



### 実行手順

#### フォルダーの確認

- Benkyoukai_Java	Web プロジェクトのバックエンド

- Benkyoukai_vue	Web プロジェクトのフロントエンド

- Design					下図

- kaiwa						音声を保存するフォルダ

- SQL						データベースsqlスクリプトのフォルダ

- uploads					アップロードした写真を保存するフォルダ



### バックエンドのデプロイ方法

1.プロジェクトをクローンします

```powershell
git clone git@github.com:c59z/Benkyoukai.git
```

2.sqlスクリプトを導入します

```sql
create database benkyoukai charset=utf8;

use benkyoukai;

source benkyoukai.sql
```

3.Mavenの依存関係をインストールします

```powershell
mvn clean install
```

4.次の内容を変更してください:

application.yml

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/benkyoukai?characterEncoding=utf-8&serverTimezone=Asia/Shanghai
    username: username # あなたのMySQLユーザー名を入力してください
    password: password # MySQLのパスワードを入力してください
    driver-class-name: com.mysql.cj.jdbc.Driver
  servlet:
    multipart:
      max-file-size: 2MB
      max-request-size: 5MB
      
      
file-save-path: D:\code\Project\SotsuGyou\uploads\   # クローンしたuploadsフォルダの絶対パス

avatar-path: D:\code\Project\SotsuGyou\uploads\avatar\ # ユーザープロフィール画像の絶対パス

kaiwa-path: D:\code\Project\SotsuGyou\kaiwa\ # 会話フォルダの絶対パス
 
```

5.プロジェクトを起動します

#### フロントエンドのデプロイ方法

注意: フロントエンドプロジェクトを起動する前に、バックエンドが正常に動作していることを確認してください

1.依存関係をインストールします

```powershell
npm install
```

2.プロジェクトを起動します

```powershell
npm run dev
```



----



## English

**Benkyoukai**

A Japanese Language Learning Blog and Conversation Practice Web Project Based on SpringBoot and Vue,Built-in Chinese, Japanese, and English language switching function

### Running Steps

#### Directory Structure

- **Benkyoukai_Java**: Backend of the web project
- **Benkyoukai_vue**: Frontend of the web project
- **Design**: Initial design drafts
- **kaiwa**: Folder containing conversation audio files
- **SQL**: Database SQL files
- **uploads**: Folder for storing images

### Backend Deployment

1.Clone the project

```powershell
git clone git@github.com:c59z/Benkyoukai.git
```

2.Create a MySQL database and import the SQL file

```sql
create database benkyoukai charset=utf8;

use benkyoukai;

source benkyoukai.sql
```

3.Install Maven dependencies

```powershell
mvn clean install
```

4.Modify the following content:

**application.yml**

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/benkyoukai?characterEncoding=utf-8&serverTimezone=Asia/Shanghai
    username: username # Replace with your MySQL username
    password: password # Replace with your MySQL password
    driver-class-name: com.mysql.cj.jdbc.Driver
  servlet:
    multipart:
      max-file-size: 2MB
      max-request-size: 5MB
      
file-save-path: D:\code\Project\SotsuGyou\uploads\   # Absolute path to the uploads folder you cloned

avatar-path: D:\code\Project\SotsuGyou\uploads\avatar\ # Absolute path to the avatar user profile pictures folder

kaiwa-path: D:\code\Project\SotsuGyou\kaiwa\ # Absolute path to the conversation folder
```

5.Start the project



### Frontend Deployment

Note: Ensure the backend is running correctly before starting the frontend project.

1.Install dependencies

```powershell
npm install
```

2.Run the project

```powershell
npm run dev
```