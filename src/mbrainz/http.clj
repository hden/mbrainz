(ns mbrainz.http
  (:require [datomic.ion.lambda.api-gateway :as apigw]
            [duct.core :as duct]
            [mbrainz.core :as core]))

(defn -get-handler
  ([] (-get-handler [:duct.profile/prod]))
  ([profiles]
   (core/init-key :duct.handler/root [:duct.profile/prod])))

(def get-handler (memoize -get-handler))
(def default-handler (get-handler))
(def lambda-proxy (apigw/ionize default-handler))
