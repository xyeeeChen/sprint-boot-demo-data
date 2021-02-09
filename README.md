# README

使用版本

* Springfox: 3.0
> Springfox 將 Swagger 封裝為可以整合至基於 Spring 生態系統的套件，並且提供了 springfox-swagger-ui 將 Swagger UI 整合至 Server 端
>
> `2.x and 3.0 的 Springfox 使用方式有些許不一樣`
>
> 除了 Springfx 以外，也可以使用 springdoc-openapi 產生 swagger API 文件

## 簡易版

查看有哪些 API 可以用

```shell script
curl -X GET http://localhost:8080
```

例如查詢分頁

```shell script
curl -X GET 'http://localhost:8080/books?page=0&size=2&sort=bookid,desc'
```

取得單筆資料

```shell script
curl -X GET http://localhost:8080/books/12
```

## Springfox:2.x

gradle

```groovy
implementation 'io.springfox:springfox-swagger2:2.9.2'
implementation 'io.springfox:springfox-swagger-ui:2.9.2'
implementation 'io.springfox:springfox-data-rest:2.9.2'
```

springfox-swagger2 產生描述 API 的 json 文件

springfox-swagger-ui 將 json 文件以 html 方式呈現

springfox-data-rest 增加讀取 Spring Data Rest 的 API

配置 SwaggerConfiguration.java

```java
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
              .select()
              .apis(RequestHandlerSelectors.any())
              .paths(PathSelectors.any())
              .build();
    }
}
```

存取網址: http://localhost:8080/swagger-ui.html

Api-docs: http://localhost:8080/v2/api-docs

## Springfox:3.0

gradle

```groovy
implementation 'io.springfox:springfox-boot-starter:3.0.0'
```

springfox-boot-starter 將原本所需得 dependency 整合為一

修改配置

```java
@Configuration
public class SwaggerConfiguration {
public SwaggerConfiguration() {}
  @Bean
  public Docket swaggerSetting() {
    return new Docket(DocumentationType.OAS_30)
      .apiInfo(apiInfo())
      .select()
      .paths(PathSelectors.any())
      .build();
  }
  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
      .title("Management System Documentation")
      .description("learning Spring Boot")
      .version("0.0")
      .build();
  }
}
```

存取網址: http://localhost:8080/swagger-ui/ or http://localhost:8080/swagger-ui/index.html

Api-docs: http://localhost:8080/v2/api-docs or http://localhost:8080/v3/api-docs

## 其他注意事項

`Q1`: book 沒有 getter/setter

專案中加入了 Lombok，若使用 Intellij 開發則要安裝 Lombok 的 plugin，否則會沒有 setter/getter 可用

`Q2`: BookRepository 沒有 findOne 可以用

在某次的 jpa 改版後， findOne 變了，可以改成以下寫法

```java
@Override
Optional<Book> findById(Integer integer);
```

以及

```java
Optional<Book> obook = bookRepository.findById(bookid);
if (obook.isPresent()) {
    Book book = obook.get();
}
```

更多解法可參考：https://www.itread01.com/content/1547236115.html
