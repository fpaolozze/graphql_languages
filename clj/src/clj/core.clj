(ns clj.core
  (:require [cheshire.core :as json]
            [clojure.java.io :as io]
            [com.walmartlabs.lacinia.pedestal :refer [service-map]]
            [com.walmartlabs.lacinia :refer [execute]]
            [com.walmartlabs.lacinia.parser.schema :refer [parse-schema]]
            [com.walmartlabs.lacinia.schema :as schema]
            [com.walmartlabs.lacinia.util :refer [attach-resolvers]]
            [io.pedestal.http :as http])
  (:import java.text.SimpleDateFormat
           java.util.Date)
  (:gen-class))

(def contracts [{:originalId "1" :amount 11.11 :status :ACTIVE :ownerId "1"}
                {:originalId "2" :amount 22.22 :status :ACTIVE :ownerId "2"}
                {:originalId "3" :amount 33.33 :status :INACTIVE :ownerId "1"}
                {:originalId "4" :amount 44.44 :status :INACTIVE :ownerId "2" }])

(defn date->str [date]
  (doto (new SimpleDateFormat "yyyy-MM-dd")
    (.format date)))

(defn str->date [date-str]
  (doto (new SimpleDateFormat "yyyy-MM-dd")
    (.parse date-str)))

(defn get-contracts [context args value]
  (let [{:keys [filter]} args]
    (if (= filter "ALL")
      {:totalCount (count contracts)
       :nodes contracts}

      (let [nodes (filter #(= % "1") contracts)]
        {:nodes nodes
         :totalCount (count nodes)}))))

(def compiled-schema
  (-> (slurp (io/resource "schema.graphql"))
      (parse-schema {:resolvers {:Query {:contracts get-contracts}}
                     :scalars {:Date {:parse str->date
                                      :serialize date->str}}})
      (attach-resolvers {:get-contracts get-contracts})
      schema/compile))

(def service (service-map compiled-schema {:graphiql true :port 4005}))

(defonce runnable-service (http/create-server service))

(defn -main [& args]
  (http/start runnable-service))
