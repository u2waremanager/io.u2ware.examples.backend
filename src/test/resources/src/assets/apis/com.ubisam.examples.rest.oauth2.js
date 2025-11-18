import $common from "@/assets/apis/com.ubisam.examples.rest..js";

import $accountsState from "@/assets/stores/accounts.js";


const name = "[com.ubisam.examples.rest.oauth2.js]"
// console.log(name);
// console.log($common);
// console.log($accountsState);

const $accounts = {

  api: {

    host() {
      return $common.api.host("VITE_API_OAUTH2");
    },

    execute(optionsBuilder) {
      return $accounts.api.host()
        .then(optionsBuilder)
        .then((e) => {
          return $common.axios.execute(e);
        })
        .then((e) => {
          return $common.axios.then(e);
        })
        .catch((e) => {
          throw $common.axios.catch(e);
        });
    },

    headers(headers, token){
      let oauth2 = (token == undefined) ? $accountsState.computed.oauth2.get() : token;
      return $common.api.auth(oauth2, headers, "headers");
    },
    params(params, token){
      let oauth2 = (token == undefined) ? $accountsState.computed.oauth2.get() : token;
      return $common.api.auth(oauth2, params, "params");
    },
    query(params, token){
      let oauth2 = (token == undefined) ? $accountsState.computed.oauth2.get() : token;
      return $common.api.auth(oauth2, params, "query");
    },

    pageable(data) {
      return $common.api.pageable(data);
    },

    link(base, data) {
      return $common.api.link(base, data);
    },
  },


  ////////////////////////////////////
  // APIs (Oath2)
  ////////////////////////////////////  
  oauth2: {
    providers() {
      return $accounts.api.execute((uri) => ({
        url: `${uri}/oauth2/providers`,
      }));
    },

    userinfo() {
      return $accounts.api.execute((uri) => ({
        url: `${uri}/oauth2/userinfo`,
        headers: $accounts.api.headers(),
      }));
    },

    login(query){
      return $accounts.api.execute((uri) => ({
        url: `${uri}/oauth2/userinfo`,
        headers: $common.api.auth(query, undefined, "headers"),
      }))
      .then(r=>{
        $accountsState.computed.oauth2.set(query);
        return $accountsState.computed.oauth2.get();
      });
    },

    logout() {
      return $accounts.api.execute((uri) => ({
        url: `${uri}/oauth2/logout`,
        headers: $accounts.api.headers(),
      }))
      .finally((r) => {
        $accountsState.computed.oauth2.set(undefined);
      });
    },
  },

    
}
export default $accounts;
