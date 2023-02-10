# 投資訊息自動交易平台

## 簡介
此平台定時接收 telegram 投資群組的最新訊息，再與 BingX 交易平台進行自動交易。

## 環境配置
作業平台: Ubuntu 22.04 LTS , Linux
<br>
程式語言: Java 17
<br>
開發環境: Eclipse
<br>
資料庫: MySQL
<br>
後端: Spring Boot
<br>
前端: HTML/CSS/Javascript、Bootstrap
<br>
資料來源:
- BingX api https://github.com/BingX-API/BingX-swap-api-doc
- Telegram api https://github.com/tdlib/td

## 專案啟動步驟
本專案使用 Eclipse 開發，因此設定以 Eclipse 為主
1. 可以先在 telegram 註冊帳號以及創一個群組做測試，訊息格式固定如下<br>
ACDC x MWS<br>
標的:DYDXUSDT<br>
開倉價格:1.982<br>
目前獲利:5.05%<br>
目前止損:1.976054<br>
套保%:3.0%<br>
套保時間:2022-07-26 18:19:02<br>
<img src="https://github.com/jaylee840831/auto_invest/blob/master/image/telegram%E8%A8%8A%E6%81%AF.png"/> <p>
2. 目前專案內部已經載好 telegram api 的 .so檔，存放在tdlib/src/main/resources/lib，若需要原始下載步驟可以至 https://github.com/tdlib/td 查看如何生成適用於 Java 專案的 library <p>
3. 設定 Native Library 的路徑，tdlib/src/main/resources/lib
<img src="https://github.com/jaylee840831/auto_invest/blob/master/image/tdlib%E8%B7%AF%E5%BE%91.png" /> <p>
4. 本地端資料庫請自行到 application.yml 設定 <p>
5. 第一次啟動專案時，到 /manage/home 平台管理畫面點選開啟自動收訊，會需要輸入要接收訊息的 telegram 帳號的手機號碼，以及驗證碼(此方式就是一般登入 telegram app 或 web 版的模式)
<img src="https://github.com/jaylee840831/auto_invest/blob/master/image/%E7%99%BB%E5%85%A5telegram.png" /> <p>
6. 重啟開發環境與專案，在資料夾 tdlib 底下會再出現一個資料夾 tdlib，這時就是已經記住帳號且登入了 <p>
7. 啟動專案後，可以先呼叫 /api/td/gcs 這支api，得到要接收訊息群組的 id (可以多重新載入此頁幾次)
<img src="https://github.com/jaylee840831/auto_invest/blob/master/image/%E5%8F%96%E5%BE%97room%20code.png" /> <p>
8. 到 application.yml 設定群組 id
<img src="https://github.com/jaylee840831/auto_invest/blob/master/image/roomcode.png" /> <p>
9. 再次重啟專案，到 /manage/home 平台管理畫面點選開啟自動收訊，即可接收訊息。可以試著點選最新訊息的 GET 按鈕看有沒有收到訊息，如果沒有，試試點選重送自動收訊請求
<img src="https://github.com/jaylee840831/auto_invest/blob/master/image/%E8%87%AA%E5%8B%95%E4%BA%A4%E6%98%93%E5%B9%B3%E5%8F%B0%E7%AE%A1%E7%90%86%E8%80%85%E7%95%AB%E9%9D%A2.png" /> <p>
<img src="https://github.com/jaylee840831/auto_invest/blob/master/image/%E6%9C%80%E6%96%B0%E8%A8%8A%E6%81%AF.png" /> <p>
10. 若已經有顯示最新一筆訊息，到 /client/register，註冊自動交易的BingX用戶。uesr id 是您在 BingX 網站【用戶中心】->【帳戶與安全】的 UID，接著您需要通過 BingX 網站【用戶中心】->【API管理(專業合約)】取得2個必須記住的信息： API Key 和 Secret Key ，兩種 key 將由隨機生成和提供。 創建完成再到本網站註冊。
<img src="https://github.com/jaylee840831/auto_invest/blob/master/image/%E8%A8%BB%E5%86%8A%E4%BA%A4%E6%98%93.png" /> <p>
11. 若註冊成功，可以在 /manage/home 的已註冊交易用戶列表中看到註冊好的用戶訊息
<img src="https://github.com/jaylee840831/auto_invest/blob/master/image/%E8%A8%BB%E5%86%8A%E4%BA%A4%E6%98%93%E5%88%97%E8%A1%A8.png" /> <p>

