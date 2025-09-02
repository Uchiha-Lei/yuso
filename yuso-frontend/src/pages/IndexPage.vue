<template>
  <div class="index-page">
    <a-input-search
      v-model:value="searchParams.text"
      placeholder="input search text"
      enter-button="Search"
      size="large"
      @search="onSearch"
    />
    <MyDivider />
    <a-tabs v-model:activeKey="activeKey" @change="onTabChange">
      <a-tab-pane key="post" tab="文章">
        <MyDivider />
        <PostList :post-list="postList" />
      </a-tab-pane>
      <a-tab-pane key="picture" tab="图片">
        <MyDivider />
        <PictureList :picture-list="pictureList" />
      </a-tab-pane>
      <a-tab-pane key="user" tab="用户">
        <MyDivider />
        <UserList :user-list="userList" />
      </a-tab-pane>
    </a-tabs>
  </div>
</template>

<script setup lang="ts">
import { ref, watchEffect } from "vue";
import PostList from "@/components/PostList.vue";
import UserList from "@/components/UserList.vue";
import PictureList from "@/components/PictureList.vue";
import MyDivider from "@/components/MyDivider.vue";
import { useRoute, useRouter } from "vue-router";
import myAxios from "@/plugins/myAxios";

// 帖子列表
const postList = ref([]);
// 图片列表
const pictureList = ref([]);
// 用户列表
const userList = ref([]);

const router = useRouter();
const route = useRoute();
const activeKey = route.params.category;
const initSearchParams = {
  text: "",
  pageSize: 10,
  pageNum: 1,
};

// /**
//  * 加载数据（分散）
//  */
// const loadDataOld = (params: any) => {
//   const postQuery = {
//     ...params,
//     searchText: params.text,
//   };
//   myAxios.post("/post/list/page/vo", postQuery).then((res: any) => {
//     postList.value = res.records;
//   });
//
//   const pictureQuery = {
//     ...params,
//     searchText: params.text,
//   };
//   myAxios.post("/picture/list/page/vo", pictureQuery).then((res: any) => {
//     pictureList.value = res.records;
//   });
//
//   const userQuery = {
//     ...params,
//     userName: params.text,
//   };
//   myAxios.post("/user/list/page/vo", userQuery).then((res: any) => {
//     userList.value = res.records;
//   });
// };

/**
 * 加载数据(统一)
 */
const loadData = (params: any) => {
  const query = {
    ...params,
    searchText: params.text,
  };

  myAxios.post("/search/all", query).then((res: any) => {
    postList.value = res.postList;
    userList.value = res.userList;
    pictureList.value = res.pictureList;
  });
};

const searchParams = ref(initSearchParams);
// 首次请求
loadData(initSearchParams);

watchEffect(() => {
  searchParams.value = {
    ...initSearchParams,
    text: route.query.text,
  } as any;
});

const onSearch = (value: string) => {
  router.push({
    query: searchParams.value,
  });
  loadData(searchParams.value);
};

const onTabChange = (key: string) => {
  router.push({
    path: `/${key}`,
    query: searchParams.value,
  });
};
</script>
