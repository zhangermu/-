var vm = new Vue({
    el:'#deptdiv',
    data:{
        deptlist:[],
        pageNum:1,
        pageSize:3,
        page:{},
        searchEntity:{},
        entity:{},
        plist:[],
        postids:[]
    },
    methods:{
        getDeptListConn:function () {
            var _this = this;
            axios.post("../dept/getDeptListConn.do?pageNum="+_this.pageNum+"&pageSize="+_this.pageSize,_this.searchEntity).then(function (response) {
                _this.pageNum=response.data.currentPage;
                _this.pageSize=response.data.pageSize;
                _this.deptlist = response.data.list;
                _this.page = response.data;
            })
        },
        paging:function (pageNum) {
            this.pageNum = pageNum;
            this.getDeptListConn();
        },
        toDeptPost:function (deptid) {
            var _this = this;
            //把当前部门查询出来，还要把当前部门的职位也查询出来
            axios.post("../dept/getDeptByDeptId.do?deptid="+deptid).then(function (response) {
                _this.entity = response.data;
                _this.postids = response.data.postids;
                _this.plist = response.data.plist;
                document.getElementById("deptpostdiv").style.display="block";
            })
        },
        saveDeptPost:function () {
            var _this = this;
            axios.post("../dept/saveDeptPost.do?deptid="+_this.entity.id,_this.postids).then(function (response) {
                if(response.data.flag){
                    document.getElementById("deptpostdiv").style.display="none";
                    _this.getDeptListConn();
                    window.location.reload();//刷新
                }else{
                    alert(response.data.msg);
                    window.location.reload();//刷新
                }
            })
        }
    }
});
vm.getDeptListConn();