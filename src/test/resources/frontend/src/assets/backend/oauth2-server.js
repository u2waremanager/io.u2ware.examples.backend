import $base from "@/assets/backend/base.js";
import $userinfo from "@/assets/stores/userinfo.js";

const name = "[/assets/backend/oauth2-server.js]";

const $server = {

  api: {

    execute(optionsBuilder) {
      return $base.meta
        .env("VITE_OAUTH2_SERVER")
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
      return `${env["VITE_OAUTH2_SERVER"]}${paths}`;
    },

    token(env){
      let token = $userinfo.computed.token.get();
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


  ////////////////////////////////////
  // APIs (Oath2)
  ////////////////////////////////////  
  oauth2: {

    available(){
      return $base.meta.env("VITE_OAUTH2_SERVER")
        .then(e=>{
          let uri = e["VITE_OAUTH2_SERVER"];

          console.log("uri");
          if(uri == undefined) {
            return undefined;
          }else{
            return uri.startsWith("http");
          }
        })
    },

    providers() {
      return $server.api.execute((e) => ({
        method: "GET",
        url: $server.api.url(e, "/oauth2/providers")  
      }));
    },

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

    login(query){
      let token = query.id_token;
      return $server.api.execute((e) => ({
          url: $server.api.url(e, "/oauth2/userinfo") ,
          headers: $base.api.headers({}, token),
      }))
      .then(r=>{
        $userinfo.computed.oauth2.set(query);
        $userinfo.computed.token.set(token);
        return r;
      });
    },

    logout() {
      return $server.api.execute((e) => ({
        url: $server.api.url(e, "/oauth2/logout") ,
        headers: $server.api.headers(e, {}),
      }))
      .finally((r) => {
        $userinfo.computed.oauth2.set(undefined);
        $userinfo.computed.token.set(undefined);
      });
    },


    publicKey(){
      return $server.api.execute((e) => ({
        url: $server.api.url(e, "/oauth2/keys/public") ,
        headers: $server.api.headers(e, {}),
      }))
    }

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
        url : $server.api.url(e, "/api/users"),
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
}
export default $server;
