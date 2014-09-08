(ns clojure-fun.main
  (:gen-class :main true)
  (:require [clojure-fun.caesar-cipher.core :as cc]))

(defn -main [& args]
  (let [[text-arg shift-arg mode] args
        text (clojure.string/upper-case text-arg)
        shift (Integer/parseInt shift-arg)
        coding-f (if (= mode "e") cc/encode cc/decode)]
    (println (cc/code-all coding-f text shift))))
