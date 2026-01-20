<template>
  <v-container class="text-center">
    <v-progress-circular
      color="primary"
      indeterminate
      v-if="providers == undefined"
    >
    </v-progress-circular>

    <v-row no-gutters v-for="provider in providers">
      <v-col cols="12" v-for="provider in providers">
        <v-btn
          variant="outlined"
          block
          size="x-large"
          @click="login(provider.uri)"
        >
          {{ $t("accounts.login.provider", [provider.name]) }}
        </v-btn>
      </v-col>
    </v-row>
    {{ providers }}
  </v-container>
</template>

<script>
const x = "[/accounts/login]";
import $oauth2Server from "@/assets/apis/oauth2-server";
import $restServer from "@/assets/apis/rest-server";

export default {
  data: () => ({
    username: undefined,
    providers: [],
  }),

  computed: {

  },

  methods: {



    
    login(uri) {
      let callback = `${window.location.origin}/accounts/logon`;
      let href = uri + callback;
      // if (this.hasTested) {
      //   href = uri
      //     .replace("{provider}", this.username)
      //     .replace("{callback}", callback);
      // } else {
      //   href = uri + callback;
      // }
      console.log(x, "login()", href);
      window.location.href = href;
    },
  },

  watch: {},

  mounted() {

    Promise.resolve()
      .then((r) => {
        console.log(x, "mounted()", 1);
        return $restServer.oauth2.userinfo();
      })
      .then((r) => {
        console.log(x, "mounted()", 2);
        this.$router.push("/");
      })
      .catch((r) => {
        console.log(x, "mounted()", 3);
        return $restServer.oauth2.providers();
      })
      .then((r) => {
        console.log(x, "mounted()", 4, r);
        if(r != undefined && r.forEach != undefined) {
          r.forEach(provider => this.providers.unshift(provider));
          return $oauth2Server.oauth2.providers();
        }
      })
      .then((r) => {
        console.log(x, "mounted()", 5, r);
        if(r != undefined && r.forEach != undefined) {
          r.forEach(provider => this.providers.unshift(provider));
        }
      })
      .catch((r) => {
        console.log(x, "mounted()", 6, r);
      })
      .finally((r) => {
        console.log(x, "mounted()", 7);
        if(this.providers.length == 1) {
          this.login(this.providers[0].uri);
        }
      })




    // Promise.resolve()
    //   .then((r) => {
    //     console.log(x, "mounted()", 1);
    //     return $accountsApi.oauth2.userinfo();
    //   })
    //   .then((r) => {
    //     console.log(x, "mounted()", 2);
    //     this.$router.push("/");
    //   })
    //   .catch((r) => {
    //     console.log(x, "mounted()", 3);
    //     return $accountsApi.oauth2.providers();
    //   })
    //   .then((r) => {
    //     console.log(x, "mounted()", 4);
    //     this.providers = r;


    //     // console.log(x, "mounted()", 5, this.providers.length);
    //     // console.log(x, "mounted()", 6, this.providers[0].uri);

    //     // if(this.providers.length == 1) {
    //     //   this.login(this.providers[0].uri);
    //     // }

    //   })
    //   .catch((r) => {
    //     console.log(x, "mounted()", 3);
    //     // this.$router.push("/");
    //   })      
  },
};
</script>
