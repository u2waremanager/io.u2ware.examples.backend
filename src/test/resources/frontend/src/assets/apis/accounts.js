import $common from "@/assets/apis/common.js";
import $commonStore from "@/assets/stores/common.js";

const name = "[/assets/apis/accounts.js]";

const $accountsApi = {

  api: {

    execute(optionsBuilder) {
      return $common.meta
        .env("VITE_API_ACCOUNTS", "VITE_API_TOKEN")
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
        return `${env["VITE_API_ACCOUNTS"]}${data}`;
      }
    },    

    token(env){

      console.log("token", 1, env);

      let t = env["VITE_API_TOKEN"];
      let token = t == undefined ? $commonStore.computed.token.get() : t;
      console.log("token", 2, token);
      return token;
    },    

    headers(env, headers) {
      let token = $accountsApi.api.token(env);
      return $common.api.headers(headers, token);
    },

    params(env, params){
      let token = $accountsApi.api.token(env);
      return $common.api.params(params, token);
    },

    query(env, query) {
      let token = $accountsApi.api.token(env);
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
    userinfo(roles) {
      return $accountsApi.api.execute((e) => ({
        method: "GET",
        url: $accountsApi.api.url(e, "/oauth2/userinfo") ,
        headers: $accountsApi.api.headers(e, {}),
      }));
    },


    providers() {
      return $accountsApi.api.execute((e) => ({
        method: "GET",
        url: $accountsApi.api.url(e, "/oauth2/providers")  
      }));
    },

    login(query){
      let token = query.id_token;

      return $accountsApi.api.execute((e) => ({
          url: $accountsApi.api.url(e, "/oauth2/userinfo") ,
          headers: $common.api.headers({}, token),
      }))
      .then(r=>{
        $commonStore.computed.oauth2.set(query);
        $commonStore.computed.token.set(token);
        return r;
      });
    },

    logout() {
      return $accountsApi.api.execute((e) => ({
        url: $accountsApi.api.url(e, "/oauth2/logout") ,
        headers: $accountsApi.api.headers(e, {}),
      }))
      .finally((r) => {
        $commonStore.computed.oauth2.set(undefined);
        $commonStore.computed.token.set(undefined);
      });
    },


  },

    
}
export default $accountsApi;
