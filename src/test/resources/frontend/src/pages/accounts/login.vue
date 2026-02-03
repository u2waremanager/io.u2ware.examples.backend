<template>
  <v-container class="text-center">
    <v-progress-circular
      color="primary"
      indeterminate
      v-if="providers == undefined"
    >
    </v-progress-circular>

    <v-row no-gutters v-for="provider in providers">
      <v-col cols="12">
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
import $oauth2Server from "@/assets/backend/oauth2-server";

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
      console.log(x, "login()", href);
      window.location.href = href;
    },
  },

  watch: {},

  mounted() {

    Promise.resolve()
      .then((r) => {
        console.log(x, "mounted()", 1);
        return $oauth2Server.oauth2.userinfo();
      })
      .then((r) => {
        console.log(x, "mounted()", 2);
        this.$router.push("/contents");
      })
      .catch((r) => {
        console.log(x, "mounted()", 3);
        return $oauth2Server.oauth2.providers();
      })
      .then((r) => {
        console.log(x, "mounted()", 4, r);
        this.providers = r;
        if(this.providers.length == 1) {
          this.login(this.providers[0].uri);
        }
      })
      .catch((r) => {
        this.$router.push("/");
      })
  },
};
</script>
