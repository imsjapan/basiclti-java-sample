# basiclti-java-sample
IMS Global Learning Tools Interoperability 1.1を利用した、LTI Tool Providerのサンプルプログラムです。
Webアプリケーションフレームワークとして、Spring Bootを利用しています。

- [Learning Tools Interoperability | IMS Global Learning Consortium](https://www.imsglobal.org/activity/learning-tools-interoperability)
- [IMSGlobal/basiclti-util-java](https://github.com/IMSGlobal/basiclti-util-java)
- [Spring Boot](https://projects.spring.io/spring-boot/)


# プログラム内容
## LtiController.java
- LMSからのローンチに成功すれば"success"、失敗すれば"error"と表示します。LTI Launchリクエストで渡された各種パラメータをコンソール(標準出力)に出力します。

## LtiWebController.java
- Web画面にLTI Launchリクエストで渡された各種パラメータを表示します。

## 1. 認証情報の検証
LTI Tool Consumerから渡されたkeyを受け取り、対応するsecretを返す。
本来であれば、渡されたキーに対応するSecretをDBや認証サーバに問い合わせて取得することが想定されるが、ここではサンプルのため、固定文字列”secret”を返却。

```MockKeyService.java
@Override
public String getSecretForKey(String key) {
  return "secret";
}
```

## 2.LMSからパラメータを受け取る
引数HttpServletRequestでLTI Tool ConsumerからのLaunchリクエストを、LtiVerificationResultで上記1の認証情報の検証結果を受け取る。
```LtiController.java
@Lti
	@RequestMapping(value="/launch", method=RequestMethod.POST)
	public String ltiEntry(HttpServletRequest request, LtiVerificationResult result) {
    ...中略...
  }
```

# 実行方法
1. Windowsであればコマンドプロンプト，Macであればターミナルでサンプルプロジェクトの直下に移動
2. サンプルプログラムの実行パッケージを作成
```
$ mvn package
```
3. targetディレクトリの中に，サンプルプログラムの実行パッケージができている
```
$ ls ./target/
classes                    maven-archiver
demo-0.0.1-SNAPSHOT.jar            maven-status
demo-0.0.1-SNAPSHOT.jar.original    surefire-reports
generated-sources            test-classes
generated-test-sources
```
4. サンプルプログラム実行パッケージを実行し，学習ツール（Spring Bootサーバ）を起動
```
$ java -jar target/demo-0.0.1-SNAPSHOT.jar
```
5. ブラウザでSakaiからアクセス，動作を確認
 - LtiController: http://[host]/launch
 - LtiWebController: http://[host]/launchweb
6. (終了方法) Ctrl + C でサーバを終了
