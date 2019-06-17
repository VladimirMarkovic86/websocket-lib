(ns websocket-lib.core)

(def base-ws-url
     (atom nil))

(defn form-ws-url
  "Form ws or wss url from baseURI"
  []
  (let [base-uri (.-baseURI
                   js/document)
        [protocol
         domain] (.split
                   base-uri
                   "://"
                   2)
        domain (.substring
                 domain
                 0
                 (.indexOf
                   domain
                   "/"))
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
      domain))
 )

(defn connect-websocket
  "Initialize js/WebSocket object"
  [websocket-uri]
  (try
    (let [ws-base-url (or @base-ws-url
                          (form-ws-url))
          ws-url (str
                   ws-base-url
                   websocket-uri)]
      (js/WebSocket.
        ws-url))
    (catch js/Error e
      (.log
        js/console
        (.-message
          e))
     ))
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
        (.log
          js/console
          event)
        (when (fn?
                onopen-fn)
          (onopen-fn
            event))
       ))
    (aset
      websocket-obj
      "onmessage"
      (fn [event]
        (.log
          js/console
          event)
        (when (fn?
                onmessage-fn)
          (onmessage-fn
            event))
       ))
    (aset
      websocket-obj
      "onerror"
      (fn [event]
        (.log
          js/console
          event)
        (when (fn?
                onerror-fn)
          (onerror-fn
            event))
       ))
    (aset
      websocket-obj
      "onclose"
      (fn [event]
        (.log
          js/console
          event)
        (when (fn?
                onclose-fn)
          (onclose-fn
            event))
       ))
    websocket-obj))

