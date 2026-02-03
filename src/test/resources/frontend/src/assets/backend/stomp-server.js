import $base from "@/assets/backend/base.js";
import $userinfo from "@/assets/stores/userinfo.js";
import $stompjs from "stompjs";

const name = "[/assets/backend/stomp-server.js]";

const $server = {
  api: {
    execute(optionsBuilder) {
      return $base.meta
        .env("VITE_STOMP_SERVER")
        .then(optionsBuilder)
        .then((e) => {
          return $base.api.execute(e);
        })
        .then((e) => {
          return $base.api.then(e);
        })
        .catch((e) => {
          throw $base.api.catch(e);
        });
    },

    url(env, path) {
      let paths = (path != null) ? path : ""
      return `${env["VITE_STOMP_SERVER"]}${paths}`;
    },

    token(env) {
      let token = $userinfo.computed.token.get();
      return token;
    },

    headers(env, headers) {
      let token = $server.api.token(env);
      return $base.api.headers(headers, token);
    },

    params(env, params) {
      let token = $server.api.token(env);
      return $base.api.params(params, token);
    },

    query(env, query) {
      let token = $server.api.token(env);
      return $base.api.query(query, token);
    },

    pageable(data) {
      return $base.api.pageable(data);
    },
  },

  /////////////////////////////////////
  //
  /////////////////////////////////////
  stomp: {
    ws: undefined,
    subscription: {},

    connect() {
      return new Promise((resolve, reject) => {
        $base.meta
          .env("VITE_STOMP_SERVER", "VITE_STOMP_DESTINATION")
          .then((r) => {
            let query = $server.api.query(r);
            let url = $server.api.url(r, "?"+query);
            // alert(url);

            $server.stomp.ws = $stompjs.client(url);
            $server.stomp.ws.connect(
              {},
              (e) => {
                let dest = r["VITE_STOMP_DESTINATION"];
                resolve(dest);
              },
              (e) => {
                reject(e);
              }
            );
          });
      });
    },

    disconnect() {
      return new Promise((resolve) => {
        if ($server.stomp.ws == undefined) resolve();
        $server.stomp.ws.disconnect(
          () => {
            $server.stomp.ws = undefined;
            resolve();
          },
          { force: true }
        );
      });
    },

    subscribe(path, callback) {
      return new Promise((resolve, reject) => {
        if ($server.stomp.ws == undefined) reject();

        let dest = path.startsWith('/topic') ? path : `/topic${path}`;

        let subscription = $server.stomp.ws.subscribe(dest, (m) => {
          let headers = m.headers;
          let payload = JSON.parse(m.body);
          callback(payload, path);
        });
        $server.stomp.subscription[path] = subscription;
        resolve(path);
      });
    },

    unsubscribe(path) {
      return new Promise((resolve, reject) => {
        if ($server.stomp.ws == undefined) reject();

        let subscription = $server.stomp.subscription[path];
        subscription.unsubscribe();
        delete $server.stomp.subscription[path];

        resolve(path);
      });
    },

    publish(path, msg) {
      return new Promise((resolve, reject) => {
        if ($server.stomp.ws == undefined) reject();

        let headers = {};
        let payload = undefined;
        try {
          payload = JSON.stringify(JSON.parse(msg));
        } catch {
          payload = JSON.stringify(msg);
        }

        let dest = path.startsWith('/app') ? path : `/app${path}`;
        $server.stomp.ws.send(dest, headers, payload);
        resolve(path);
      });
    },
  },
};

export default $server;
