<template>
  <v-container class="text-center">
    <v-btn variant="outlined" block size="x-large" @click="logout">
      {{ $t("accounts.logout.title") }}
    </v-btn>
  </v-container>
</template>

<script>
const x = "[/accounts/logout]";
import $oauth2Server from "@/assets/backend/oauth2-server";

export default {
  methods: {
    logout() {
      let before = this.$t("accounts.logout.title");
      let after = this.$t("accounts.logoff.title");

      this.$dialog
        .confirm(before)
        .then((r) => {
          console.log(x, "logout()", 1, r);
          return $oauth2Server.oauth2.logout();
        })
        .then((r) => {
          console.log(x, "logout()", 2, r);
          return this.$dialog.alert(after);
        })
        .then((r) => {
          console.log(x, "logout()", 3, r);
          this.$router.push("/accounts/logoff");
        })
        .catch((r) => {
          console.log(x, "logout()", 4, r);
          this.$router.push("/accounts/logoff");
        })
        ;
    },
  },

  mounted() {
    Promise.resolve()
      .then((r) => {
        console.log(x, "mounted()", 1);
        return $oauth2Server.oauth2.userinfo();
      })
      .then((r) => {
        console.log(x, "mounted()", 2);
        // OK. show page
      })
      .catch((r) => {
        this.$router.push("/");
      });
  },
};
</script>
