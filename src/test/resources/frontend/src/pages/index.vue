<template>
  <v-app id="inspire">
    <v-app-bar app>
      <router-link to="/">
        <U2wareAvatar></U2wareAvatar>
      </router-link>

      <v-toolbar-title> {{ $t("index.bar.title") }} </v-toolbar-title>

      <v-spacer></v-spacer>

      <v-btn text variant="elevated" color="error" @click="start">
        <v-icon>mdi-account</v-icon> 시작하기
      </v-btn>
    </v-app-bar>

    <U2wareFooter></U2wareFooter>

    <v-main>



    </v-main>
  </v-app>


</template>

<script>
const x = "[/]";
import $oauth2Server from "@/assets/backend/oauth2-server";

export default {
  data: () => ({}),

  computed: {

  },

  methods: {
    start() {
      $oauth2Server.oauth2
        .userinfo()
        .then((r) => {
          console.log(x, "start()", 1, r);
          this.$router.push(`/contents`);
        })
        .catch((r) => {
          return $oauth2Server.oauth2.available(r)
            .then((r) => {
              if(true == r) {
                console.log(x, "start()", 2, r);
                this.$router.push(`/accounts/login`);

              }else if(false == r) {
                console.log(x, "start()", 3, r);
                this.$dialog.alert("관리자 기능 비활성화");         

              }else{
                console.log(x, "start()", 4, r);
                this.$router.push(`/contents`);
              }
            })
        })
        ;
    },
  },

  watch: {},

  mounted() {


  },
};
</script>