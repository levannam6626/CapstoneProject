<template>
  <div class="bill-view">
    <select class="select-overview" v-model="overView" v-if="this.loginRole === 'SELLER'">
      <option value="Report Overview">Report Overview</option>
      <option value="Bill List">Bill List</option>
    </select>
    <div class="statistics" v-if="this.productOrders.length > 0 && this.loginRole === 'SELLER' && overView === 'Report Overview'">
      <div class="action">
        <select v-model="selected">
          <option value="Sales">Sales</option>
          <option value="Quantity">Quantity</option>
        </select>
        <div v-if="this.selected === 'Sales'">
          <span style="font-weight: bold;">TOTAL SALES: </span>
          <span style="color: blue; font-weight: bold; font-size: 22px;">${{ this.totalSales }}</span>
        </div>
        <div v-else>
          <span style="font-weight: bold;">TOTAL QUANTITY SOLD: </span>
          <span style="color: blue; font-weight: bold; font-size: 22px;">{{ this.totalQuantity }}</span>
        </div>
      </div>
      <div class="charts">
        <div class="details-sales" v-if="this.selected === 'Sales'">
          <div class="details-annual-sales">
            <div class="sales">
              <ChartComponent :data="dataYearSales"
                :options="{ title: { display: true, text: 'Details of annual sales' }, responsive: true, maintainAspectRatio: false, }"
                :type="'line'" />
            </div>
            <div class="totalsales">
              <ChartComponent :data="dataYearTotalSales"
                :options="{ title: { display: true, text: 'Total annual sales' }, responsive: true, maintainAspectRatio: false, }"
                :type="'bar'" />
            </div>
          </div>
          <div class="details-month">
            <div class="details-month-sales">
              <ChartComponent :data="dataMonthSales"
                :options="{ title: { display: true, text: 'Details of sales in the last 30 days' }, responsive: true, maintainAspectRatio: false, }"
                :type="'line'" />
            </div>
          </div>
        </div>
        <div v-else>
          <div class="details-annual-quantity">
            <div class="quantity">
              <ChartComponent :data="dataYearQuantity"
                :options="{ title: { display: true, text: 'Detailed annual quanlity' }, responsive: true, maintainAspectRatio: false, }"
                :type="'bar'" />
            </div>
            <div class="total-quantity">
              <ChartComponent :data="dataYearTotalQuantity"
                :options="{ title: { display: true, text: 'Total annual quanlity' }, responsive: true, maintainAspectRatio: false, }"
                :type="'bar'" />
            </div>
          </div>
          <div class="details-month">
            <div class="details-month-quantity">
              <ChartComponent :data="dataMonthQuantity"
                :options="{ title: { display: true, text: 'Details of quantity in the last 30 days' }, responsive: true, maintainAspectRatio: false, }"
                :type="'bar'" />
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="bills" v-if="this.loginRole === 'CUSTOMER' || overView === 'Bill List'">
      <div class="bills-display">
        <select style="font-weight: bold;" :class="deliveryCss(deliveryStatus)" v-model="deliveryStatus">
          <option style="padding: 10px 0; color: blue; background-color: #fff;" value="all">ALL</option>
          <option style="padding: 10px 0; background-color: lightgreen;" value="pending">PENDING</option>
          <option style="background-color: green;" value="awaiting_shipment">AWAITING</option>
          <option style="background-color: blue;" value="completed">COMPLETED</option>
          <option style="background-color: red;" value="canceled">CANCELED</option>
        </select>
        <div style="display: flex; width: auto; align-items: center;">
          <span style="font-weight: bold;">TOTAL BILL: </span>
          <p style="text-align: right; margin: 0; width: 4.2rem; color: blue; font-weight: bold; font-size: 22px;">{{ this.productOrdersFilter.length }} / {{this.productOrders.length}}</p>
        </div>
      </div>
      <Carousel style="width: 100%; text-align: left !important;" v-bind="settings" :breakpoints="breakpoints" v-if="this.productOrdersDisplay.length > 0">
        <Slide v-for="(productOrder, index) in this.productOrdersDisplay" :key="index">
          <div class="carousel__item"><BillItem :product-order="productOrder" /></div>
        </Slide>
        <template #addons>
          <Navigation style="color: red;"/>
        </template>
      </Carousel>
      <div class="pagination-product">
        <el-pagination
          :page-size="pagination.pageSize"
          :pager-count="pagination.pagerCount"
          :page-count="totalPage"
          layout="prev, pager, next"
          v-model:current-page="pagination.page"
          @current-change="getPageBills()"
        />
      </div>
    </div>
  </div>
</template>
<script>
import BillItem from "@/components/BillItem";
import ChartComponent from '@/components/ChartComponent.vue'

import { Carousel, Navigation, Slide } from 'vue3-carousel'
import 'vue3-carousel/dist/carousel.css'

import store from "@/store";
import { mapActions } from "vuex";

export default {
  data() {
    return {
      loginRole: store.state.auth.userAccount.role,
      selected: 'Sales',
      pagination: {
        pageSize: 9,
        pagerCount: 2,
        page: 1
      },
      settings: {
        itemsToShow: 1,
        snapAlign: 'center',
      },
      breakpoints: {
        // 900 and up
        900: {
          itemsToShow: 2,
          snapAlign: 'center',
        },
        // 1124 and up
        1124: {
          itemsToShow: 3,
          snapAlign: 'start',
        },
      },
      overView: 'Report Overview',
      deliveryStatus: 'all'
    }
  },
  components: {
    BillItem,
    ChartComponent,
    Carousel,
    Slide,
    Navigation,
  },
  computed: {
    productOrders() {
      return store.state.order.productOrders;
    },
    productOrdersFilter() {
      if(this.deliveryStatus !== 'all') {
        return this.productOrders.filter(productOrder => {
          return productOrder.status === this.deliveryStatus.toUpperCase();
        });
      } else {
        return this.productOrders;
      }
    },
    productOrdersDisplay() {
      const startIndex = (this.pagination.page - 1) * this.pagination.pageSize;
      const endIndex = startIndex + this.pagination.pageSize;
      return this.productOrdersFilter.slice(startIndex, endIndex);
    },
    totalPage() {
      return Math.max(Math.ceil(this.productOrdersFilter.length / this.pagination.pageSize), 1);
    },
    productOrdersStatistic() {
      return this.productOrders.filter(productOrder => {
        return productOrder.status === "COMPLETED";
      });
    },
    totalSales() {
      let totalSales = 0;
      for (let index = this.productOrdersStatistic.length - 1; index >= 0; index--) {
        const productOrder = this.productOrdersStatistic[index];
        totalSales += productOrder.totalPrice;
      }
      return Math.round(totalSales * 100) / 100;
    },
    totalQuantity() {
      let totalQuantity = 0;
      for (let index = this.productOrdersStatistic.length - 1; index >= 0; index--) {
        const productOrder = this.productOrdersStatistic[index];
        for (let index = 0; index < productOrder.order_items.length; index++) {
          const order_item = productOrder.order_items[index];
          totalQuantity += order_item.quantity;
        }
      }
      return totalQuantity;
    },
    dataYearSales() {
      if (this.productOrdersStatistic.length > 0) {
        const colors = ['red', 'blue', '#3cba9f', '#e8c3b9'];
        const datasets = [];
        let datas = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
        let tmpYear = this.productOrdersStatistic[this.productOrdersStatistic.length - 1].createdDate.substr(0, 4);
        let tmpIndex = 0;
        for (let index = this.productOrdersStatistic.length - 1; index >= 0; index--) {
          const productOrder = this.productOrdersStatistic[index];
          const year = productOrder.createdDate.substr(0, 4);
          const month = productOrder.createdDate.substr(5, 2);
          if (year === tmpYear) {
            if (index !== 0) {
              datas[parseInt(month) - 1] = Math.round((datas[parseInt(month) - 1] + productOrder.totalPrice) * 100) / 100;
            } else {
              datas[parseInt(month) - 1] = Math.round((datas[parseInt(month) - 1] + productOrder.totalPrice) * 100) / 100;
              datasets.push(
                {
                  data: datas,
                  label: tmpYear,
                  borderColor: colors[tmpIndex]
                }
              );
            }
          } else {
            datasets.push(
              {
                data: datas,
                label: tmpYear,
                borderColor: colors[tmpIndex]
              }
            );
            tmpYear = year;
            tmpIndex++;
            datas = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
            datas[parseInt(month) - 1] = Math.round((datas[parseInt(month) - 1] + productOrder.totalPrice) * 100) / 100;
          }
        }
        return {
          labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
          datasets: datasets
        }
      }
      return {
        labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
        datasets: {
          data: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
          label: 0,
          borderColor: "red"
        }
      }
    },
    dataYearTotalSales() {
      const colors = ['red', 'blue', '#3cba9f', '#e8c3b9'];
      const datas = [];
      const labels = [];
      const datasets = [];
      let tmpIndex = -1;
      let tmpYear = null;
      for (let index = 0; index < this.productOrdersStatistic.length; index++) {
        const productOrder = this.productOrdersStatistic[index];
        const year = productOrder.createdDate.substr(0, 4);
        if (year === tmpYear) {
          datas[tmpIndex] = Math.round((datas[tmpIndex] + productOrder.totalPrice) * 100) / 100;
        } else {
          tmpIndex++;
          labels.push(year);
          datas.push(productOrder.totalPrice);
          tmpYear = year;
        }
      }
      let indexColor = 0;
      for (let index = labels.length - 1; index >= 0; index--) {
        const label = labels[index];
        datasets.push({
          label: label,
          data: [datas[index]],
          backgroundColor: colors[indexColor++]
        })
      }
      return {
        labels: ['sales'],
        datasets: datasets
      }
    },
    dataYearQuantity() {
      if (this.productOrdersStatistic.length > 0) {
        const colors = ['#3cba9f', '#db4b0f', 'red', 'blue'];
        const datasets = [];
        let datas = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
        let tmpYear = this.productOrdersStatistic[this.productOrdersStatistic.length - 1].createdDate.substr(0, 4);
        let tmpIndex = 0;
        for (let index = this.productOrdersStatistic.length - 1; index >= 0; index--) {
          const productOrder = this.productOrdersStatistic[index];
          const year = productOrder.createdDate.substr(0, 4);
          const month = productOrder.createdDate.substr(5, 2);
          if (year === tmpYear) {
            if (index !== 0) {
              for (let index = 0; index < productOrder.order_items.length; index++) {
                const order_item = productOrder.order_items[index];
                datas[parseInt(month) - 1] = datas[parseInt(month) - 1] + order_item.quantity;
              }
            } else {
              for (let index = 0; index < productOrder.order_items.length; index++) {
                const order_item = productOrder.order_items[index];
                datas[parseInt(month) - 1] = datas[parseInt(month) - 1] + order_item.quantity;
              }
              datasets.push(
                {
                  data: datas,
                  label: tmpYear,
                  backgroundColor: colors[tmpIndex]
                }
              );
            }
          } else {
            datasets.push(
              {
                data: datas,
                label: tmpYear,
                backgroundColor: colors[tmpIndex]
              }
            );
            tmpYear = year;
            tmpIndex++;
            datas = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
            for (let index = 0; index < productOrder.order_items.length; index++) {
              const order_item = productOrder.order_items[index];
              datas[parseInt(month) - 1] = datas[parseInt(month) - 1] + order_item.quantity;
            }
          }
        }
        return {
          labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
          datasets: datasets
        }
      }
      return {
        labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
        datasets: {
          data: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
          label: 0,
          backgroundColor: "red"
        }
      }
    },
    dataYearTotalQuantity() {
      const colors = ['#3cba9f', '#db4b0f', 'red', 'blue'];
      const datas = [];
      const labels = [];
      const datasets = [];
      let tmpIndex = -1;
      let tmpYear = null;
      for (let index = 0; index < this.productOrdersStatistic.length; index++) {
        const productOrder = this.productOrdersStatistic[index];
        const year = productOrder.createdDate.substr(0, 4);
        if (year === tmpYear) {
          for (let index = 0; index < productOrder.order_items.length; index++) {
            const order_item = productOrder.order_items[index];
            datas[tmpIndex] += order_item.quantity;
          }
        } else {
          tmpIndex++;
          labels.push(year);
          let tmpQuantity = 0;
          for (let index = 0; index < productOrder.order_items.length; index++) {
            const order_item = productOrder.order_items[index];
            tmpQuantity += order_item.quantity;
          }
          datas.push(tmpQuantity);
          tmpYear = year;
        }
      }
      let tmpindexColors = 0;
      for (let index = labels.length - 1; index >= 0; index--) {
        const label = labels[index];
        datasets.push({
          label: label,
          data: [datas[index]],
          backgroundColor: colors[tmpindexColors++]
        })
      }
      return {
        labels: ['quantity'],
        datasets: datasets
      }
    },
    dataMonthSales() {
      const datas = [];
      const labels = [];
      let tmpIndex = -1;
      let tmpDay = null;
      let tmpMon = null;
      for (let index = 0; index < this.productOrdersStatistic.length && tmpIndex < 30; index++) {
        const productOrder = this.productOrdersStatistic[index];
        const month = productOrder.createdDate.substr(5, 2);
        const day = productOrder.createdDate.substr(8, 2);
        if (day === tmpDay && month === tmpMon) {
          datas[tmpIndex] = Math.round((datas[tmpIndex] + productOrder.totalPrice) * 100) / 100;
        } else {
          tmpIndex++;
          labels.push(day + '/' + month);
          datas.push(productOrder.totalPrice);
          tmpDay = day;
          tmpMon = month
        }
      }
      return {
        labels: labels.reverse(),
        datasets: [{
          label: 'sales($)',
          data: datas.reverse(),
          borderColor: '#f87679'
        }]
      }
    },
    dataMonthQuantity() {
      const datas = [];
      const labels = [];
      let tmpIndex = -1;
      let tmpDay = null;
      let tmpMon = null;
      for (let index = 0; index < this.productOrdersStatistic.length && tmpIndex < 30; index++) {
        const productOrder = this.productOrdersStatistic[index];
        const month = productOrder.createdDate.substr(5, 2);
        const day = productOrder.createdDate.substr(8, 2);
        if (day === tmpDay && month === tmpMon) {
          for (let index = 0; index < productOrder.order_items.length; index++) {
            const order_item = productOrder.order_items[index];
            datas[tmpIndex] = datas[tmpIndex] + order_item.quantity;
          }
        } else {
          tmpIndex++;
          labels.push(day + '/' + month);
          let tmpQty = 0;
          for (let index = 0; index < productOrder.order_items.length; index++) {
            const order_item = productOrder.order_items[index];
            tmpQty = tmpQty + order_item.quantity;
          }
          datas.push(tmpQty);
          tmpDay = day;
          tmpMon = month
        }
      }
      return {
        labels: labels.reverse(),
        datasets: [{
          label: 'quantity',
          data: datas.reverse(),
          backgroundColor: '#f87679'
        }]
      }
    }
  },
  methods: {
    ...mapActions('order', ['loadAllBillAction', 'loadBillByUserAction']),
    async loadAllBill() {
      await this.loadAllBillAction();
      console.log(this.productOrders)
    },
    async loadBillByUser() {
      await this.loadBillByUserAction();
    },
    deliveryCss(status) {
      switch (status.toUpperCase()) {
        case 'ALL':
          return 'all-css';
        case 'AWAITING_SHIPMENT':
          return 'awaiting-css';
        case 'COMPLETED':
          return 'completed-css'
        case 'CANCELED':
          return 'canceled-css';
        default:
          return 'pending-css';
      }
    },
    getPageBills() {
      console.log(this.pagination.page)
    }
  },
  mounted() {
    if (store.state.auth.userAccount.role === 'CUSTOMER') {
      this.loadBillByUser();
    } else {
      this.loadAllBill();
    }
  }
}
</script>
<style scoped>
.bill-view {
  box-sizing: border-box;
  width: 100%;
}
.select-overview {
  box-sizing: border-box;
  height: 2rem;
  width: 10rem;
  border-radius: 5px;
  text-align: center;
  color: blue;
  margin-bottom: 10px;
  border: 1px groove #b1adad;
  font-weight: bold;
  font-size: 15px;
}
.select-overview:focus {
  outline: none;
}
.statistics select {
  box-sizing: border-box;
  width: 8rem;
  height: 2rem;
  border-radius: 5px;
  text-align: center;
  border: 1px groove #b1adad;
  font-size: 15px;
  color: blue;
}

.statistics select:focus {
  outline: none;
}

option {
  box-sizing: border-box;
  font-size: 15px;
}

.statistics {
  box-sizing: border-box;
  width: 100%;
}

h2 {
  margin-top: 0;
}

.action {
  display: flex;
  align-items: center;
  justify-content: space-between;
  box-sizing: border-box;
  padding: 0 10px;
  border-radius: 5px;
  background-color: #fff;
  width: 100%;
  height: 3rem;
  margin-bottom: 20px;
}

.details-month {
  display: flex;
  justify-content: space-between;
}

.details-annual-sales,
.details-annual-quantity {
  display: flex;
  justify-content: space-between;
  margin-bottom: 30px;
}

.details-month-sales,
.details-month-quantity {
  width: 100%;
}

.sales,
.totalsales,
.quantity,
.total-quantity,
.details-month-sales,
.details-month-quantity {
  box-sizing: border-box;
  padding: 0 10px;
  border-radius: 8px;
  background-color: #fff;
}

.sales,
.quantity {
  width: 68%;
}

.totalsales,
.total-quantity {
  width: 28%;
}

.bills {
  position: relative;
  height: auto;
  min-height: 21.2rem;
  width: 100%;
  box-sizing: border-box;
}
.bills-display {
  display: flex;
  align-items: center;
  justify-content: space-between;
  box-sizing: border-box;
  padding: 0 10px;
  border-radius: 5px;
  height: 3rem;
  width: 100%;
  background-color: #fff;
}
.bills-display select{
  box-sizing: border-box;
  height: 2rem;
  border-radius: 5px;
  text-align: center;
  width: 8rem;
  border: 1px groove #b1adad;
}
.bills-display select:focus{
  outline: none;
}
.bills-display select > option{
  font-weight: bold;
  border: 1px solid gray;
  padding: 10px;
  color: #fff;
}
.all-css {
  color: blue;
  background-color: #fff;
}
.pending-css {
  color: black;
  background-color: lightgreen;
}
.awaiting-css {
  color: white;
  background-color: green;
}
.completed-css {
  color: white;
  background-color: blue;
}
.canceled-css {
  color: white;
  background-color: red;
}
.pagination-product {
  margin-top: 10px;
  display: flex;
  justify-content: center;
}
</style>
<style>
.carousel__prev, .carousel__next {
  margin: 0;
}
.carousel .carousel__icon {
  color: red !important;
  font-weight: bold;
  background-color: #fff !important;
  border: 1px solid #adacac;
  box-sizing: border-box;
  height: 35px;
}
</style>

