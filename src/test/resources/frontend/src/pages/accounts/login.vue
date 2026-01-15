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
  </v-container>
</template>

<script>
const x = "[/accounts/login]";
import $accountsApi from "@/assets/apis/accounts";

export default {
  data: () => ({
    username: undefined,
    providers: undefined,
  }),

  computed: {

  },

  methods: {



    
    login(uri) {
      let href = "";
      let callback = `${window.location.origin}/accounts/logon`;
      if (this.hasTested) {
        href = uri
          .replace("{provider}", this.username)
          .replace("{callback}", callback);
      } else {
        href = uri + callback;
      }
      console.log(x, "login()", href);
      window.location.href = href;
    },
  },

  watch: {},

  mounted() {
    Promise.resolve()
      .then((r) => {
        console.log(x, "mounted()", 1);
        return $accountsApi.oauth2.userinfo();
      })
      .then((r) => {
        console.log(x, "mounted()", 2);
        this.$router.push("/");
      })
      .catch((r) => {
        console.log(x, "mounted()", 3);
        return $accountsApi.oauth2.providers();
      })
      .then((r) => {
        console.log(x, "mounted()", 4);
        this.providers = r;
      })
      .catch((r) => {
        console.log(x, "mounted()", 3);
        this.$router.push("/");
      })      
  },
};
</script>
