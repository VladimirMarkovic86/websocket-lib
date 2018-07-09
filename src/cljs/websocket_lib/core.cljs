(ns websocket-lib.core)

(defn form-ws-url
  "Form ws or wss url from baseURI"
  [http-uri]
  (let [base-uri (aget js/document "baseURI")
        [protocol
         domain] (.split base-uri "://" 2)
        domain (.replace domain "/" "")
        final-ws-url (atom "")]
    (if (= protocol
           "https")
      (swap!
        final-ws-url
        str
        "wss://")
      (swap!
        final-ws-url
        str
        "ws://"))
    (swap!
      final-ws-url
      str
      domain
      http-uri))
 )

(defn connect-websocket
 "Initialize js/WebSocket object"
 [websocket-uri]
 (js/WebSocket.
   (form-ws-url
     websocket-uri))
 )

(defn websocket
  "Connect to server via websocket and register
    onopen, onmessage, onerror and onclose events with functions"
  [websocket-uri
   {onopen-fn :onopen-fn
    onmessage-fn :onmessage-fn
    onerror-fn :onerror-fn
    onclose-fn :onclose-fn}]
  (let [websocket-obj (connect-websocket
                        websocket-uri)]
    (aset
      websocket-obj
      "onopen"
      (fn [event]
        (.log js/console event)
        (when (fn? onopen-fn)
          (onopen-fn
            event))
        ))
    (aset
      websocket-obj
      "onmessage"
      (fn [event]
        (.log js/console event)
        (when (fn? onmessage-fn)
          (onmessage-fn
            event))
       ))
    (aset
      websocket-obj
      "onerror"
      (fn [event]
        (.log js/console event)
        (when (fn? onerror-fn)
          (onerror-fn
            event))
       ))
    (aset
      websocket-obj
      "onclose"
      (fn [event]
        (.log js/console event)
        (when (fn? onclose-fn)
          (onclose-fn
            event))
       ))
   ))

