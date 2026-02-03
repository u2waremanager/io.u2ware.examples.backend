<template>
    <v-autocomplete
        v-model="values1"
        :items="items1"
        label="Default"
    ></v-autocomplete>
{{ values1 }}

    <v-autocomplete
        label="aaaaaa"
        v-model="values2"
        :items="items2"
        item-value="key"
        item-title="name"
    >
    </v-autocomplete>
{{ values2 }}
       

  <v-autocomplete
    v-model="select"
    v-model:search="search"
    :loading="loading"
    :items="items2"
    item-value="key"
    item-title="name"
    autocomplete="off"
    @update:modelValue="method4"
  ></v-autocomplete>



</template>

<script>

export default {

  data: () => ({

    items1 : ['foo', 'bar', 'fizz', 'buzz'],
    values1 : "bar",


    items2 : [
        { key : "k1", name : "foo" },
        { key : "k2", name : "bar" },
        { key : "k3", name : "fizz" },
        { key : "k4", name : "buzz" }
    ],
    // values2 : { name : "buzz", key : "k4"}
    values2 : { key : "k3", name : "fizz", hello: "world"},


    values3: undefined,

    select: "k1",
    search: undefined,
    loading : false,
  }),

  methods : {

    method1(a, b, c){
        console.log("method1", a, b, c);
        if(b == undefined) {
            return false;
        }else{
            if(a.key == b.key){
                return true;
            }else{
                return false;
            }
        }
    },
    method2(a, b){
        console.log("method2", a, b);
        return a.name+"_"+a.key;
    },
    method3(a){
        console.log("method3", a);
        return a;
    },
    method4(a){
        console.log("method4", a);
        // this.loading = false;
    }
  },

  mounted(){
  }
}

</script>
