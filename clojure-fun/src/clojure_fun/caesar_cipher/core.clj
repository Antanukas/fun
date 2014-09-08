(ns clojure-fun.caesar-cipher.core
  (:require clojure.set))

;letter constants
(def letters "ABCDEFGHIJKLMNOPQRSTUVWXYZ")
(def letters-to-code
  (zipmap letters (range (count letters))))
(def code-to-letters (clojure.set/map-invert letters-to-code))

;Encoding and decoding
(defn coder [letter shift coding-f]
  (let [letter-code (get letters-to-code letter)
        encoded-code (mod (coding-f letter-code shift) (count letters))]
    (get code-to-letters encoded-code)))

(defn encode [letter shift]
  (coder letter shift +))
(defn decode [letter shift]
  (coder letter shift -))

(defn code-all [coding-f text shift]
  (apply str (map #(coding-f %1 shift) text)))