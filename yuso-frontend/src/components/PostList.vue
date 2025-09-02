<template>
  <a-list
    item-layout="vertical"
    size="large"
    :pagination="pagination"
    :data-source="props.postList"
  >
    <template #renderItem="{ item }">
      <a-list-item key="item.title">
        <template #actions>
          <component :is="type" style="margin-right: 8px" />
          喜欢：{{ item.favourNum }} |&nbsp;&nbsp;&nbsp;点赞：
          {{ item.thumbNum }}
        </template>
        <template #extra>
          <img width="272" height="180" alt="logo" :src="xusheng" />
        </template>
        <a-list-item-meta :description="item.description">
          <!--标题链接-->
          <template #title>
            <a :href="item.href">{{ item.title }}</a>
          </template>
          <template #avatar><a-avatar :src="usagi" /></template>
        </a-list-item-meta>
        {{ item.content }}
      </a-list-item>
    </template>
  </a-list>
</template>

<script setup lang="ts">
import xusheng from "@/assets/xusheng.jpg";
import usagi from "@/assets/usagi.jpg";
import { withDefaults, defineProps } from "vue";

interface Props {
  postList: any[];
}

const props = withDefaults(defineProps<Props>(), {
  postList: () => [],
});
</script>
<style scoped></style>
