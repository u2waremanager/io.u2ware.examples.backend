import $common from "@/assets/apis/common.js";
import $commonStore from "@/assets/stores/common.js";

// import $contentsStore from "@/assets/stores/contents.js";

const name = "[/assets/apis/contents.js]";

const $server = {
  api: {
    execute(optionsBuilder) {
      return $common.meta
        .env("VITE_REST_SERVER", "VITE_REST_SERVER_TOKEN")
        .then(optionsBuilder)
        .then((e) => {
          return $common.api.execute(e);
        })
        .then((e) => {
          return $common.api.then(e);
        })
        .catch((e) => {
          throw $common.api.catch(e);
        });
    },

    url(env, data) {
      if (typeof data == "object") {
        return `${data._links.self.href}`;
      } else {
        return `${env["VITE_REST_SERVER"]}${data}`;
      }
    },

    token(env){
      let t = env["VITE_REST_SERVER_TOKEN"];
      let token = t == undefined ? $commonStore.computed.token.get() : t;
      return token;
    }, 

    headers(env, headers) {
      let token = $server.api.token(env);
      return $common.api.headers(headers, token);
    },

    params(env, params){
      let token = $server.api.token(env);
      return $common.api.params(params, token);
    },

    query(env, query) {
      let token = $server.api.token(env);
      return $common.api.query(query, token);
    },

    pageable(data) {
      return $common.api.pageable(data);
    },
  },


  /////////////////////////////////////
  //
  /////////////////////////////////////
  oauth2: {

    providers() {
      return $server.api.execute((e) => ({
        method: "GET",
        url: $server.api.url(e, "/oauth2/providers")  
      }));
    },

    userinfo(role) {
      return $server.api.execute((e) => ({
        method: "GET",
        url: $server.api.url(e, "/oauth2/userinfo"),
        headers: $server.api.headers(e, {}),        
      })).then(r => {
        if(role == undefined) return r;       

        let roles = (r.roles != undefined) ? r.roles : r.claims.authorities;
        let username = r.username != undefined ? r.username : r.claims.sub;
        r["username"] = username;

        let idx = roles.findIndex(e => e == role);
        if(idx > -1) {
          return r;
        }else{
          throw r;
        }
      });
    },
    
    login(query){
      let token = query.id_token;

      return $server.api.execute((e) => ({
          url: $server.api.url(e, "/oauth2/userinfo") ,
          headers: $common.api.headers({}, token),
      }))
      .then(r=>{
        $commonStore.computed.oauth2.set(query);
        $commonStore.computed.token.set(token);
        return r;
      });
    },

    logout() {
      return $server.api.execute((e) => ({
        url: $server.api.url(e, "/oauth2/logout") ,
        headers: $server.api.headers(e, {}),
      }))
      .finally((r) => {
        $commonStore.computed.oauth2.set(undefined);
        $commonStore.computed.token.set(undefined);
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
        url: $server.api.url(e, data),
        headers: $server.api.headers(e, {}),
      }));
    },
    update(data) {
      return $server.api.execute((e) => ({
        method: "PUT",
        url: $server.api.url(e, data),
        headers: $server.api.headers(e, {}),
        data: data,
      }));
    },
    delete(data) {
      return $server.api.execute((e) => ({
        method: "DELETE",
        url: $server.api.url(e, data),
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
        url: $server.api.url(e, data),
        headers: $server.api.headers(e, {}),
      }));
    },
    update(data) {
      return $server.api.execute((e) => ({
        method: "PUT",
        url: $server.api.url(e, data),
        headers: $server.api.headers(e, {}),
        data: data,
      }));
    },
    delete(data) {
      return $server.api.execute((e) => ({
        method: "DELETE",
        url: $server.api.url(e, data),
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
        url: $server.api.url(e, data),
        headers: $server.api.headers(e, {}),
      }));
    },
    update(data) {
      return $server.api.execute((e) => ({
        method: "PUT",
        url: $server.api.url(e, data),
        headers: $server.api.headers(e, {}),
        data: data,
      }));
    },
    delete(data) {
      return $server.api.execute((e) => ({
        method: "DELETE",
        url: $server.api.url(e, data),
        headers: $server.api.headers(e, {}),
      }));
    },
  },

  /////////////////////////////////////
  //
  /////////////////////////////////////  
  sessions: {
    search(data, params) {
      return $server.api
        .execute((e) => ({
          method: "POST",
          url: $server.api.url(e, "/api/sessions/search"),
          headers: $server.api.headers(e, {}),
          params: $server.api.pageable(params),
          data: data,
        }));
    },
  },  

  /////////////////////////////////////
  //
  /////////////////////////////////////
  users: {
    search(data, params) {
      return $server.api
        .execute((e) => ({
          method: "POST",
          url: $server.api.url(e, "/api/users/search"),
          headers: $server.api.headers(e, {}),
          params: $server.api.pageable(params),
          data: data,
        }));
    },
    create(data) {
      return $server.api.execute((e) => ({
        method: "POST",
        url: $server.api.url(e, "/api/users"),
        headers: $server.api.headers(e, {}),
        data: data,
      }));
    },
    read(data) {
      return $server.api.execute((e) => ({
        method: "POST",
        url: $server.api.url(e, data),
        headers: $server.api.headers(e, {}),
      }));
    },
    update(data) {
      return $server.api.execute((e) => ({
        method: "PUT",
        url: $server.api.url(e, data),
        headers: $server.api.headers(e, {}),
        data: data,
      }));
    },
    delete(data) {
      return $server.api.execute((e) => ({
        method: "DELETE",
        url: $server.api.url(e, data),
        headers: $server.api.headers(e, {}),
      }));
    },
  },


  /////////////////////////////////////
  //
  /////////////////////////////////////
  tokens: {
    search(data, params) {
      return $server.api
        .execute((e) => ({
          method: "POST",
          url: $server.api.url(e, "/api/tokens/search"),
          headers: $server.api.headers(e, {}),
          params: $server.api.pageable(params),
          data: data,
        }));
    },
    create(data) {
      return $server.api.execute((e) => ({
        method: "POST",
        url: $server.api.url(e, "/api/tokens"),
        headers: $server.api.headers(e, {}),
        data: data,
      }));
    },
    read(data) {
      return $server.api.execute((e) => ({
        method: "POST",
        url: $server.api.url(e, data),
        headers: $server.api.headers(e, {}),
      }));
    },
    update(data) {
      return $server.api.execute((e) => ({
        method: "PUT",
        url: $server.api.url(e, data),
        headers: $server.api.headers(e, {}),
        data: data,
      }));
    },
    delete(data) {
      return $server.api.execute((e) => ({
        method: "DELETE",
        url: $server.api.url(e, data),
        headers: $server.api.headers(e, {}),
      }));
    },
  },

};

export default $server;