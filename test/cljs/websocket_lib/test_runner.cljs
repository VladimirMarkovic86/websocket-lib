(ns websocket-lib.test-runner
  (:require [websocket-lib.core-test-cljs]
            [doo.runner :refer-macros [doo-tests doo-all-tests]]))

(enable-console-print!)

(doo-tests
  'websocket-lib.core-test-cljs)

