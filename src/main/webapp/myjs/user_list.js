var vm = new Vue({
    el:'#userdiv',
    data:{
        userlist:[],
        entity:{},
        dlist:[{postids:[]}],
        deptids:[]

    },
    methods:{
        getUserList:function () {
            var _this = this;
            axios.get("../user/getUserList.do").then(function (response) {
                _this.userlist = response.data;
            });
        },
        toUserDept:function (id) {
            //查出这个用户已经拥有的部门id，和全部部门，查询一个对象，完全可以，也可以把user丰富一下，直接查询一个user
            var _this = this;
            axios.get('../user/getUserById.do?id='+id).then(function (response) {
                _this.entity = response.data;
                _this.dlist = response.data.dlist;
                _this.deptids=response.data.deptids;
                document.getElementById("userdeptdiv").style.display="block";
            });
        },
        saveUserDept:function () {
            var _this = this;
            axios.post("../user/saveUserDept.do?id="+_this.entity.id,_this.deptids).then(function (response) {
                if(response.data.flag){
                    document.getElementById("userdeptdiv").style.display="none";
                    _this.getUserList();
                    window.location.reload();//刷新
                }else{
                    alert(response.data.msg);
                    window.location.reload();//刷新
                }
            })

        },
        toUserPost:function (id) {
            alert(id);
            var _this = this;
            axios.get('../user/getUserInfo.do?id='+id).then(function (response) {
                _this.entity = response.data;
                _this.dlist = response.data.dlist;
                document.getElementById("userpostdiv").style.display="block";
            });
        },
        saveUserPost:function () {
            this.entity.dlist = this.dlist;
            var _this =this;
            axios.post('../user/saveUserPost.do',_this.entity).then(function (response) {
                if(response.data.flag){
                    alert(response.data.msg);
                    document.getElementById("userpostdiv").style.display="none";
                }else{
                    alert(response.data.msg);
                }
            });
        }
    }
});
vm.getUserList();
