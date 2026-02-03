import $base from "@/assets/backend/base.js";
import $userinfo from "@/assets/stores/userinfo.js";

// import $contentsStore from "@/assets/stores/contents.js";

const name = "[/assets/backend/example-server.js]";

const $server = {
  api: {
    execute(optionsBuilder) {
      return $base.meta
        .env("VITE_EXAMPLE_SERVER")
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
      return `${env["VITE_EXAMPLE_SERVER"]}${paths}`;
    },

    token(env) {
      let token = $userinfo.computed.token.get();
      // alert(token);
      return token;
    },

    headers(env, headers) {
      let token = $server.api.token(env);
      return $base.api.headers(headers, token);
    },

    params(env, params){
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
  oauth2: {

    userinfo(role) {
      return $server.api.execute((e) => ({
        method: "GET",
        url: $server.api.url(e, "/oauth2/userinfo") ,
        headers: $server.api.headers(e, {}),
      })).then(r => {
        if(role == undefined) return r;   
        if(r.authorities == undefined) return r;
        r[role] = r.authorities.findIndex(e => e == role) > -1;

        return r;
      });
    },
  },


  /////////////////////////////////////
  //
  /////////////////////////////////////
  foos: {
    search(data, params) {
      return $server.api
        .execute((e) => ({
          method: "GET",
          url: $server.api.url(e, "/api/foos"),
          headers: $server.api.headers(e, {}),
          params: $server.api.pageable(params),
          data: data,
        }));
    },
    create(data) {
      return $server.api.execute((e) => ({
        method: "POST",
        url: $server.api.url(e, "/api/foos"),
        headers: $server.api.headers(e, {}),
        data: data,
      }));
    },
    read(data) {
      return $server.api.execute((e) => ({
        method: "GET",
        url: data._links.self.href,
        headers: $server.api.headers(e, {}),
      }));
    },
    update(data) {
      return $server.api.execute((e) => ({
        method: "PUT",
        url: data._links.self.href,
        headers: $server.api.headers(e, {}),
        data: data,
      }));
    },
    delete(data) {
      return $server.api.execute((e) => ({
        method: "DELETE",
        url: data._links.self.href,
        headers: $server.api.headers(e, {}),
      }));
    },
  },

  /////////////////////////////////////
  //
  /////////////////////////////////////
  bars: {
    search(data, params) {
      return $server.api
        .execute((e) => ({
          method: "POST",
          url: $server.api.url(e, "/api/bars/search"),
          headers: $server.api.headers(e, {}),
          params: $server.api.pageable(params),
          data: data,
        }));
    },
    create(data) {
      return $server.api.execute((e) => ({
        method: "POST",
        url: $server.api.url(e, "/api/bars"),
        headers: $server.api.headers(e, {}),
        data: data,
      }));
    },
    read(data) {
      return $server.api.execute((e) => ({
        method: "POST",
        url: data._links.self.href,
        headers: $server.api.headers(e, {}),
      }));
    },
    update(data) {
      return $server.api.execute((e) => ({
        method: "PUT",
        url: data._links.self.href,
        headers: $server.api.headers(e, {}),
        data: data,
      }));
    },
    delete(data) {
      return $server.api.execute((e) => ({
        method: "DELETE",
        url: data._links.self.href,
        headers: $server.api.headers(e, {}),
      }));
    },
  },

  /////////////////////////////////////
  //
  /////////////////////////////////////
  items: {
    search(data, params) {
      return $server.api
        .execute((e) => ({
          method: "POST",
          url: $server.api.url(e, "/api/items/search"),
          headers: $server.api.headers(e, {}),
          params: $server.api.pageable(params),
          data: data,
        }));
    },
    create(data) {
      return $server.api.execute((e) => ({
        method: "POST",
        url: $server.api.url(e, "/api/items"),
        headers: $server.api.headers(e, {}),
        data: data,
      }));
    },
    read(data) {
      return $server.api.execute((e) => ({
        method: "POST",
        url: data._links.self.href,
        headers: $server.api.headers(e, {}),
      }));
    },
    update(data) {
      return $server.api.execute((e) => ({
        method: "PUT",
        url: data._links.self.href,
        headers: $server.api.headers(e, {}),
        data: data,
      }));
    },
    delete(data) {
      return $server.api.execute((e) => ({
        method: "DELETE",
        url: data._links.self.href,
        headers: $server.api.headers(e, {}),
      }));
    },
  },


  /////////////////////////////////////
  //
  /////////////////////////////////////
  nodes: {
    search(data, params) {
      return $server.api
        .execute((e) => ({
          method: "POST",
          url: $server.api.url(e, "/api/nodes/search"),
          headers: $server.api.headers(e, {}),
          params: $server.api.pageable(params),
          data: data,
        }));
    },
    create(data) {
      return $server.api.execute((e) => ({
        method: "POST",
        url: $server.api.url(e, "/api/nodes"),
        headers: $server.api.headers(e, {}),
        data: data,
      }));
    },
    read(data) {
      return $server.api.execute((e) => ({
        method: "POST",
        url:data._links.self.href,
        headers: $server.api.headers(e, {}),
      }));
    },
    update(data) {
      return $server.api.execute((e) => ({
        method: "PUT",
        url: data._links.self.href,
        headers: $server.api.headers(e, {}),
        data: data,
      }));
    },
    delete(data) {
      return $server.api.execute((e) => ({
        method: "DELETE",
        url: data._links.self.href,
        headers: $server.api.headers(e, {}),
      }));
    },
  },


  /////////////////////////////////////
  //
  /////////////////////////////////////
  accounts: {
    search(data, params) {
      return $server.api
        .execute((e) => ({
          method: "POST",
          url: $server.api.url(e, "/api/accounts/search"),
          headers: $server.api.headers(e, {}),
          params: $server.api.pageable(params),
          data: data,
        }));
    },
    create(data) {
      return $server.api.execute((e) => ({
        method: "POST",
        url: $server.api.url(e, "/api/accounts"),
        headers: $server.api.headers(e, {}),
        data: data,
      }));
    },
    read(data) {
      return $server.api.execute((e) => ({
        method: "POST",
        url: data._links.self.href,
        headers: $server.api.headers(e, {}),
      }));
    },
    update(data) {
      return $server.api.execute((e) => ({
        method: "PUT",
        url: data._links.self.href,
        headers: $server.api.headers(e, {}),
        data: data,
      }));
    },
    delete(data) {
      return $server.api.execute((e) => ({
        method: "DELETE",
        url: data._links.self.href,
        headers: $server.api.headers(e, {}),
      }));
    },
  },

};

export default $server;