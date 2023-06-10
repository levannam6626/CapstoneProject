<template>
  <ul class="pagination">
    <li class="pagination-item">
      <button type="button" style="font-weight: bold;font-size:20px;" @click="onClickPreviousPage"
        :disabled="isInFirstPage" :id="isInFirstPage">
        &lt;
      </button>
    </li>
    <li class="pagination-item" v-for="page in pages" :key="page.name">
      <button type="button" @click="onClickPage(page.name)" :disabled="page.isDisabled">
        {{ page.name }}
      </button>
    </li>
    <li class="pagination-item">
      <button type="button" style="font-size:20px;" @click="onClickNextPage" :disabled="isInLastPage" :id="isInLastPage">
        &gt;
      </button>
    </li>
  </ul>
</template>
<script>
export default {
  props: {
    maxVisibleButtons: {
      type: Number,
      required: false,
      default: 3
    },
    totalPages: {
      type: Number,
      required: true,
    },
    currentTable: {
      type: Number,
      required: true
    }
  },
  data() {
    return {
      currentPage: this.currentTable,
    }
  },
  computed: {
    startPage() {
      // When on the first page
      if (this.currentPage === 1) {
        return 1;
      }
      if (this.currentPage === this.totalPages && this.totalPages > 2) {
        return this.totalPages - 2;
      }
      // When in between
      return this.currentPage - 1;
    },
    pages() {
      const range = [];
      if (this.totalPages < 5) {
        for (let i = this.startPage; i <= Math.min(this.startPage + this.maxVisibleButtons - 1, this.totalPages); i += 1) {
          range.push({
            name: i,
            isDisabled: i === this.currentPage
          });
        }
      } else {
        range.push({
          name: 1,
          isDisabled: this.currentPage === 1,
        });
        if (this.currentPage !== 1 && this.currentPage !== this.totalPages) {
          range.push({
            name: '...',
            isDisabled: true
          });
          range.push({
            name: this.currentPage,
            isDisabled: true
          });
          range.push({
            name: '...',
            isDisabled: true
          });
        } else {
          range.push({
            name: '...',
            isDisabled: true
          });
        }
        range.push({
          name: this.totalPages,
          isDisabled: this.currentPage === this.totalPages,
        });
      }
      return range;
    },
    isInFirstPage() {
      return this.currentPage === 1;
    },
    isInLastPage() {
      return this.currentPage === this.totalPages;
    },
  },
  methods: {
    onClickFirstPage() {
      this.$emit('pageChanged', this.currentPage = 1);
    },
    onClickPreviousPage() {
      this.$emit('pageChanged', this.currentPage -= 1);
    },
    onClickPage(page) {
      this.$emit('pageChanged', this.currentPage = page);
    },
    onClickNextPage() {
      this.$emit('pageChanged', this.currentPage += 1);
    },
    onClickLastPage() {
      this.$emit('pageChanged', this.currentPage = this.totalPages);
    }
  }
};
</script>
<style scoped>
.pagination {
  list-style-type: none;
  height: 40px;
  display: flex;
  align-items: center;
  align-content: center;
}
.pagination-item {
  display: inline-block;
}
button[type='button'] {
  color: #42baee;
  font-size: 17px;
  border: 0;
  height: 30px;
  background-color: white;
}
button[disabled] {
  font-weight: bold;
  color: #42baee;
}
button[id='true'] {
  font-weight: normal;
  color: #468FAF;
}
</style>

