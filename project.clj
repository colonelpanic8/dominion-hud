(defproject dominion-hud "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/clojurescript "1.9.946"]
                 [org.clojure/core.async "0.4.474"]
                 [binaryage/chromex "0.5.15"]
                 [binaryage/devtools "0.9.8"]
                 [figwheel "0.5.14"]
                 [environ "1.1.0"]
                 [com.cemerick/piggieback "0.2.1"]
                 [org.clojure/clojure "1.7.0"]]

  :plugins [[lein-cljsbuild "1.1.6"]
            [lein-figwheel "0.5.14"]
            [lein-shell "0.5.0"]
            [lein-environ "1.1.0"]
            [lein-cooper "1.2.2"]]

  :test-paths ["tests/dominion-hud"]

  :source-paths ["src"]

  :clean-targets ^{:protect false} ["target"
                                    "resources/unpacked/compiled"
                                    "resources/release/compiled"]

  :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}

  :cljsbuild {:builds {}}

  :profiles {:unpacked
             {:cljsbuild {:builds
                          {:content-script
                           {:source-paths ["src"]
                            :compiler     {:output-to     "resources/unpacked/compiled/content-script/main.js"
                                           :output-dir    "resources/unpacked/compiled/content-script"
                                           :asset-path    "compiled/content-script"
                                           :main          dominion-hud.content-script
                                        ;:optimizations :whitespace ; content scripts cannot do eval / load script dynamically

                                        ; let's use advanced build with
                                        ; pseudo-names for now, there seems to
                                        ; be a bug in deps ordering
                                        ; under :whitespace mode
                                           :optimizations :advanced
                                           :pseudo-names  true
                                           :pretty-print  true}}}}}
             :test
             {:cljsbuild {:builds
                          {:test
                           {:source-paths ["src" "tests"]
                            :compiler {:output-to "resources/test/compiled.js"
                                       :main dominion-hud.test
                                       :optimizations :simple
                                       :pretty-print true}}}}
              :test-commands {"test" ["phantomjs"
                                    ; Files will be crated later:
                                    "resources/test/test.js"
                                    "resources/test/test.html"]}}
             :checkouts
             ; DON'T FORGET TO UPDATE scripts/ensure-checkouts.sh
             {:cljsbuild {:builds
                          {:content-script {:source-paths ["checkouts/cljs-devtools/src/lib"
                                                           "checkouts/chromex/src/lib"
                                                           "checkouts/chromex/src/exts"]}}}}

             :figwheel
             {:figwheel {:server-port    6888
                         :server-logfile ".figwheel.log"
                         :repl           true}}

             :disable-figwheel-repl
             {:figwheel {:repl false}}

             :cooper
             {:cooper {"content-dev"     ["lein" "content-dev"]
                       "browser"         ["scripts/launch-test-browser.sh"]}}

             :release
             {:env       {:chromex-elide-verbose-logging "true"}
              :cljsbuild {:builds
                          {:content-script
                           {:source-paths ["src/content_script"]
                            :compiler     {:output-to     "resources/release/compiled/content-script.js"
                                           :output-dir    "resources/release/compiled/content-script"
                                           :asset-path    "compiled/content-script"
                                           :main          dominion-hud.content-script
                                           :optimizations :advanced
                                           :elide-asserts true}}}}}}

  :aliases {"dev-build"       ["with-profile" "+unpacked,+checkouts,+checkouts-content-script" "cljsbuild" "once"]
            "content"         ["with-profile" "+unpacked," "cljsbuild" "auto" "content-script"]
            "build-tests"     ["with-profile" "+test," "cljsbuild" "auto"]
            "content-dev"     ["with-profile" "+unpacked,+checkouts-content-script" "cljsbuild" "auto"]
            "devel"           ["with-profile" "+cooper" "do" ; for mac only
                               ["shell" "scripts/ensure-checkouts.sh"]
                               ["cooper"]]
            "release"         ["with-profile" "+release" "do"
                               ["clean"]
                               ["cljsbuild" "once" "background" "popup" "content-script"]]
            "package"         ["shell" "scripts/package.sh"]})
