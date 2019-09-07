(defproject clj "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [cheshire "5.9.0"]
                 [com.walmartlabs/lacinia "0.34.0"]
                 [org.apache.logging.log4j/log4j-slf4j-impl "2.12.1"]
                 [org.apache.logging.log4j/log4j-api "2.12.1"]
                 [org.apache.logging.log4j/log4j-core "2.12.1"]
                 [com.walmartlabs/lacinia-pedestal "0.12.0"]]
  :main ^:skip-aot clj.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
