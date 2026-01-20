import $common from "@/assets/apis/common.js";
import $commonStore from "@/assets/stores/common.js";

const name = "[/assets/apis/accounts.js]";

const $server = {

  api: {

    execute(optionsBuilder) {
      return $common.meta
        .env("VITE_OAUTH2_SERVER")
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
        return `${env["VITE_OAUTH2_SERVER"]}${data}`;
      }
    },    

    token(env){

      // console.log("token", 1, env);

      // let t = env["VITE_API_TOKEN"];
      // let token = t == undefined ? $commonStore.computed.token.get() : t;
      // console.log("token", 2, token);
      // return token;
      return undefined;
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


  ////////////////////////////////////
  // APIs (Oath2)
  ////////////////////////////////////  
  oauth2: {
    providers() {
      return $server.api.execute((e) => ({
        method: "GET",
        url: $server.api.url(e, "/oauth2/providers")  
      }));
    },
    
    userinfo(roles) {
      return $server.api.execute((e) => ({
        method: "GET",
        url: $server.api.url(e, "/oauth2/userinfo") ,
        headers: $server.api.headers(e, {}),
      }));
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

    
}
export default $server;
