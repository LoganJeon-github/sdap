<template>
  <v-main style="margin: 2%">
    <aside style="margin-right: 20%;">
      <v-toolbar flat >
        <v-toolbar-title >{{currentDirectory}}</v-toolbar-title>
      </v-toolbar>
      <v-table density="compact">
        <thead>
          <tr>
            <th>
              Name
            </th>
            <th>
              Created At
            </th>
            <th>
              Modified At
            </th>
          </tr>
        </thead>
        <tbody>
          <tr 
            v-for="folder in folders" 
            :key="folder"
            @click="enterFolder(folder.id)"
          >
            <td>{{ folder.name }}</td>
            <td>{{ folder.createdAt }}</td>
            <td>{{ folder.modifiedAt }}</td>
          </tr>
        </tbody>
      </v-table>
      <!-- <ul>
        <li v-for="folder in folders" :key="folder">
          <div>
            {{ folder.name }}
            <v-icon icon="mdi-trash-can-outline" @click="removeItem(folder.id)"/>
          </div>
        </li>
      </ul> -->
    </aside>
  </v-main>
</template>

<script>
export default {
  data() {
    return {
      Error : false,
      currentDirectory: '/ ',
      // folders: [
      //   { id: 0, name: "Project A", parentId: null },
      //   { id: 1, name: "Project B", parentId: null},
      //   { id: 2, name: "Project C", parentId: 0 },
      //   { id: 3, name: "Project D", parentId: 2 },
      // ],
      folders: null,
    }
  },
  created() {
    this.setUpData();
  },
  methods: {
    setUpData: function(){
      this.$axios.get('/finder/folders/65534').then((data) =>{
        this.folders = data.data;
        console.log(this.folders);
      })
    },
    enterFolder: function(id){
      this.currentDirectory += this.findFolderForId(id).name +" / ";
      this.$axios.get('/finder/folders/'+id).then((data)=>{
        this.folders = data.data;
        console.log(this.folders);
      })
    },
    findFolderForId: function(id){
      for (let i = 0 ; i < this.folders.length; ++i){
        if (this.folders[i].id == id){
          return this.folders[i];
        }
      }
    },
    findFolderForName: function(name){
      for (let i = 0 ; i < this.folders.length; ++i){
        if (this.folders[i].name == name){
          return this.folders[i];
        }
      }
    },
    findFolderForparent: function(parentId){
      for (let i = 0 ; i < this.folders.length; ++i){
        if (this.folders[i].parentId == parentId){
          return this.folders[i];
        }
      }
    },
    addItem: function(e){
      var value = e.target.value;
      console.log(value);
      if (value == ""){
        console.log("isEmpty");
        return false;
      }
      if (this.itemExist(value) == true){
        this.Error = true
        console.log("isExist");
        return false;
      }
      this.Error = false;
      var tempJson = {};
      tempJson.id = this.folders.length +1;
      tempJson.name = value;
      this.folders.push(tempJson);
      e.target.value = "";
    },
    itemExist: function(value){
      for (let i = 0 ; i < this.folders.length ;++i){
        if (this.folders[i].name == value){
          return true;
        }
      }
      return false;
    },
    removeItem: function (folderId) {
      for(let i = 0 ; i < this.folders.length ; ++i){
        if (this.folders[i].id == folderId){
          this.folders.splice(i,1);
        }
      }
    }
  },
}
</script>

<style>

</style>