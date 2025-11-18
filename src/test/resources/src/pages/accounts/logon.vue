<template>
    <v-container class="text-center">
        <v-progress-circular color="primary" indeterminate>
        </v-progress-circular>
    </v-container>


</template>

<script>
const x = "[/accounts/logon]";
import $accounts from "@/assets/apis/com.ubisam.examples.rest.oauth2.js";

export default {

  computed: {
  },


    mounted() {

        this.subtitle = this.$t("accounts.login.title");


        Promise.resolve()
            .then((r) => {
                console.log(x, "mounted()", 1);
                return $accounts.oauth2.userinfo();
            })
            .then((r) => {
                console.log(x, "mounted()", 2);
                return this.$router.push("/");
            })
            .catch((r) => {
                // OK. action !!
                console.log(x, "mounted()", 3, this.$route.query);
                return $accounts.oauth2.login(this.$route.query);
            })
            .then((r) => {
                console.log(x, "mounted()", 4, r);
                if(r != undefined) this.$router.push("/contents");
            })
            .catch((r) => {
                console.log(x, "mounted()", 5, r);
                this.$router.push("/");
            });
    }

}
</script>