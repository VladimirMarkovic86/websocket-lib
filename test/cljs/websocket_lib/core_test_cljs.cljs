(ns websocket-lib.core-test-cljs
  (:require [clojure.test :refer-macros [deftest is testing]]
            [websocket-lib.core :refer [form-ws-url connect-websocket websocket]]))

(deftest test-form-ws-url
  (testing "Test form ws url"
    
    (let [result (form-ws-url)]
      
      (is
        (= result
           "ws://localhost:9876")
       )
      
     )
    
   ))

(deftest test-connect-websocket
  (testing "Test connect websocket"
    
    (let [websocket-uri nil
          result (connect-websocket
                   websocket-uri)]
      
      (is
        (instance?
          js/WebSocket
          result)
       )
      
      (is
        (= (aget
             result
             "url")
           "ws://localhost:9876/")
       )
      
     )
    
    (let [websocket-uri "/test-url"
          result (connect-websocket
                   websocket-uri)]
      
      (is
        (instance?
          js/WebSocket
          result)
       )
      
      (is
        (= (aget
             result
             "url")
           "ws://localhost:9876/test-url")
       )
      
     )
    
   ))

(deftest test-websocket
  (testing "Test websocket"
    
    (let [websocket-uri nil
          onopen-fn nil
          onmessage-fn nil
          onerror-fn nil
          onclose-fn nil
          result (websocket
                   websocket-uri
                   {:onopen-fn onopen-fn
                    :onmessage-fn onmessage-fn
                    :onerror-fn onerror-fn
                    :onclose-fn onclose-fn})]
      
      (is
        (instance?
          js/WebSocket
          result)
       )
      
     )
    
   ))

