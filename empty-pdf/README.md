空白PDF生成ツール
=================

空白1ページのPDFファイルを生成するツールです。
生成したPDFファイルは、サーバ印刷でOCR紙の背景として使用します。

ビルド
------

### 準備

- Java11

### ビルド手順

```
$ mvn clean package
```

使用方法
--------

```
$ java -jar target/empty-pdf-0.1.0-jar-with-dependencies.jar -h
Usage: empty-pdf [-hV] [-o=<path>] <size>
空白1ページのPDFを生成します。
      <size>            生成するページサイズ: A4
  -h, --help            Show this help message and exit.
  -o, --output=<path>   出力先ファイルパス(デフォルト: 'empty.pdf')
  -V, --version         Print version information and exit.
```

A4サイズのPDFファイルを生成する場合:

```
$ java -jar target/empty-pdf-0.1.0-jar-with-dependencies.jar -o target/a4.pdf A4
PDFを生成しました: target/a4.pdf
```
