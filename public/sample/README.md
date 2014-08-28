simpleWebDevTool
================


## pre-setup(if you need)

* install node.js (via nvm)
* install npm
* install grunt-cli, bower `npm install -g grunt-cli bower`

## setup

1. `npm install`
2. `cd app`
3. `bower install`
4. `cd ../`

### check

* `grunt server` -> launch normal-server and jsonAPI-server (via proxy)
* `grunt` -> test and build

trouble:

* `Warning: watch ENOSPC` -> watch range is over, 
do the command `echo fs.inotify.max_user_watches=524288 | sudo tee -a /etc/sysctl.conf && sudo sysctl -p`
http://stackoverflow.com/questions/16748737/grunt-watch-error-waiting-fatal-error-watch-enospc


## 他フレームワークの個人的見解

全体として、やっぱりフレームワーク間の移行は難しい。
（できないことはないが、フレームワークの魅了が半減する。）

既存のフレームワークは、できることを増やすよりも簡単に書けるようにするのが主。
学習コストをペイしようと思ったら、そのフレームワークにどっぷり浸かることになる。

### AngularJS

フルスタックなMVVMフレームワーク

* Data-binding
* Test環境の充実（DI, Karma）
* Filter, sort, ng-repeat
* watch（データの変更監視）
* routing, ajax
* validation
* Mobile レスポンシブ対応（？）

とか備えている。2.0系の開発が続いていて、化けるかもしれない。

#### メリット

* Data-bindingで裏でよろしくやってくれる。
* DOMをあまり意識しなくていい。
* 世界的に人気が高い。→Componentも充実している。

#### デメリット

* 学習コストが高い。
* ComponentをAngular仕様にする（Directiveを定義する）が難しい。
* 他のライブラリや設計を取り入れづらい。
* 重厚。

#### 感想

* 裏でよろしく動きすぎていて、eventやデータ変更が把握しきれない。
（watchとかfilterとか、手を加えようとすると鬼門。）
* 学習コストの割に楽になる気がしない。
* Angularにロックインされる。
（Data-bindingだけしたければVue.js使え）


### BackBone.js

* ひと通り揃っている。かつ軽量。
* 「大規模開発向け」と言われている。

#### メリット

* ある程度の書き方が強制される分、スパゲッティになりにくい（らしい）
* 日本語の資料が充実している。利用実績が多い。
* 記述量が減る。

#### デメリット

* 書き方を強制される。
* Controllerを介しない、状態監視でイベント発火したりするため、eventを追いにくいかも。

#### 感想

* 普通に便利だが、全部は要らないと思う（CollectionとかModelとかは代替可能。）
* DOM操作とかはノータッチなのでｊQueryには依存する。
* 一部だけ導入できるならアリ（router, eventHandler）。差し替えてもいいかも。
* 学習コスト低め。


### Knockout.js

* MVVMフレームワークと言われているが、ただのライブラリ
* ModelとDOMとを自動で同期させる。
* （Angularのデータバインディング・Vue.jsと同じような感じ）

#### メリット

* View（DOM）の操作とJavaScriptの操作を切り離せる。
* idやclassの指定をしなくて済む。

#### デメリット
* 人気ない。
* 特徴薄い。

#### 感想
Vue.jsでいいじゃん。どの設計とも同居できそうなのでハードルは低いか。

### ember.js

Angularと同様に、フルスタックでData-bindingもあって、
だいたいのことはできる。

#### メリット

* Angular.jsと同じ。

#### デメリット
* 人気ない。資料少ない。
* 学習コスト高いらしい。

#### 感想
未検証ですが、これならAngularでいいかな。


### ｊQueryそのまま

フレームワークとは違うけども。。。

#### メリット
* 理解しやすい（みんなもうｊQuery慣れてるでしょ？）
* 依存が少ない。
* やっぱりｊQueryプラグインが多いのは魅力。

#### デメリット
* 記述量が多い。データの反映を逐一書かなくてはいけない。
* スパゲッティ化する。
* id classでDOMが汚れる。

#### 感想

* ｊQueryでDOMの操作とajax通信とか配列の処理とか一度にさせるからでは？
* 使うべきでないメソッドを切ればいいのでは（カスタムビルド）。
* どうせどのフレームワークでも使うことになるんでしょ？

## 設計思想

### 冗長でもわかりやすく

ちょっと手間がかかるが、理解しやすくして潜在バグ・不可解な動きをなくす。

Layerをちゃんと分けて細分化してライブラリを組み替えていくことで、コードの寿命を伸ばす。学習コストを減らす。

処理の最適化などは行っていないが、
DOM操作以外はボトルネックにならないだろうとして、
多少冗長でも丁寧に書く。（JSの罠はlodashで回避）

DOMの描画が遅い場合は、Componentの記述だけ書き直す。

### コーディングルールで縛る。

Angularなど、フレームワーク側で構造が縛られていれば下手なことはしにくいが、
どうせJavascriptなので強制力は乏しい＆ルールは必須。

それなら学習コストも少ないのでルールのみで縛る。
（Routingを分けているので、Global汚染しなければ影響でないはず。）

### Single-Page-Application

サーバーとクライアントを分離させ、サーバー側のテンプレートエンジンに依存しない。
行うのはJsonのやりとりのみ。
scriptの再読み込みが発生しないため、軽い。

後述するが、状態を持たないようにすることでカオスになりにくくなるハズ。

minifyするので、役割毎にファイルを細かく分割できる。

### Data-Bindingは極力使わない。

記述が減って便利だけど、双方向の場合、Modelの構造がDOMに依存してしまう。
（プロパティ名を強制されたり、変なプロパティが付いてきたり。）

裏で処理するので、流れを追いにくいことがある。
（記述量が増えるのとどちらがいいかと言われると微妙。。
　ただ、簡単なfilterとか、全然別のところへの即時反映とか、本当に必要？）

Data-Bindingで本来やりたいことは、PureなModelが書き換わったらViewに反映させることだと思うが、
Dataでなく、EventでBindingすることで、Modelの変更Eventがあれば反映させる。

**記述の簡略化のために部分的にVue.jsなどを使うのはアリ。**

http://tech.nitoyon.com/ja/blog/2014/07/18/data-binding/

### ライブラリ

フレームワークを使うと、ロックインされ変更に弱くなる。

ライブラリは、それ用の記述を覚えるのはコストだが、
他のアプリ・フレームワークでも使える汎用的なライブラリだけ使うことで学習コストを下げる。

* jQuery (2系) -> DOM操作 各種コンポーネント
* Lodash -> JSのハマりどころを避ける・Template ≒ underscore.js
* bacon -> Event・非同期通信の制御
* sammy -> SPA用のRoutingを担う。他のライブラリで代用可。
* QUnit -> テスト時のみ利用。

他、用途に応じてコンポーネント用のPluginなどを使っていく。


以上を使って、なるべくコアを薄くして、用途ごとにSingle-Page-Applicationを複数用意する形にする。
（○○機能ではグラフなどのライブラリを追加したアプリ。
✕✕機能ではanimationに特化したアプリ、etc...）


## プログラム構成

* Controller
* Service
* Component
* DAO

基本的には、
ユーザーが操作→Componentイベント発火→Controllerで必要なデータを受ける
→Serviceで演算する(→DaoでServer通信)→Controllerにデータを返す
→Componentにデータを割りあてる→変更があれば再描画。

### Controller

ページごとに一つ割り当てられる。
そのページで使うコンポーネント・イベントは全てここに記載する。
Serviceに処理を投げてComponentにデータを返す。

EventはbaconのEventStreamで管理する。

### Model(data)

特に管理しない。（状態を保持しない）
必要があればComponent（DOM）やDAOから取ってくる。
処理した結果はまたDOMに反映させる。

### Service

DOMやデータ処理を担う。

生JSを触るとめんどくさそうなので、lodashのメソッドを多用する。
ここの処理はボトルネックになりにくいので、わかりやすい記述を意識する。

### Component

DOMのデータ反映・取得を担う。ｊQueryは基本ココでだけ使う。
Component内で閉じないEventはEventStreamでControllerに流す。

（デメリットとして、DOMとのやりとりが頻発するので速度の劣化があるかもしれない。
　解決策としては、Componentの中にpureなデータをcacheしておいて、差分だけ反映させる。
　記述がめんどくさいが、必要最小限を書けるし調整しやすい。API読んでｊQuery書くだけなので敷居は低い。）

### DAO

外部（サーバー）との通信を担う。
同期の場合はLoadでそのまま表示させる。
非同期の場合は、bacon.jsでPromiseをラップして返す。

### Template

Routingした後に割り当てるHTML。

Idを振った箱を用意しておくだけ。
（後はControllerがComponentを設置する）

### route

app/appRoute.js

データの再読み込みなしで、ページに応じたControllerとTemplateを割り当てる。

### test

単純なUnitTestのみを行います。
振る舞いテストとかは今は考えてないです。
（Angularとかをちゃんと学べばKarmaとかでブラウザテストやDIまでサポートしているが、重いかなと。）


## 懸念点

### 複雑なDOMのListとか組むのが大変では？

jqueryで書くと複雑になる場合は、部分的にVue.jsを使って動的にDOMを作っていくのもいいかもしれない。
それか、Componentの中でTemplate_HTMLを書いて子コンポーネントを載せる。（複雑になりがち）

ただ、そもそも一画面に状態を持ちすぎるのは混乱のもとなので、画面を分割することを考える。
再読み込みはほとんどしないので、ページ遷移は速いはず。

### 車輪の再発明にならないか？

俺々フレームワークっぽくなっていますが、目的としては一つに依存しない、プラガブルな基盤を目指しています。

問題となるのはDOMの取り扱いだけだと思うので、既にネットに転がっているベストプラクティスは再利用できるかと。

### DOMに思いっきり依存しないか？

DOMとのやりとりはComponentの中だけで完結させる。

PureなModelは無理だと思っているので、DOM依存を前提としてデータを取ってくる。

DOMの影響は、Controllerは一部だけ、Service・Daoは一切受けない。


## 開発環境

### bower
パッケージ管理ツール。mavenみたいな。
基本、ライブラリはこれを使って落としてくる。
もし既存の依存関係とぶつかるのであれば既存のを優先する（ｊQueryのバージョンとか。）

### grunt ビルド

ひと通りのテストとminifyなどのビルド作業をやってくれる。

### grunt server

PlayのDevモードに相当する。
サーバーサイド不要で、minify前のJSなどで簡単に動作させられる。
また、APIサーバーを立ち上げてproxyかましているので、
Ajax通信を模した開発を行える。


## TODO

* tab sample (or data-cache across the pages)
* speed test(view rendering)
* BackBone routing
