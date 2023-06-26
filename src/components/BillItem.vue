<template>
	<div class="bill-item">
		<p style="display: flex; align-items: center; height: 3em; width: 9em; background-color: #00000A; color: #fff; font-weight: bold; box-sizing: border-box; padding-left: 2em;">BILL</p>
		<p style="padding-right: 2em; margin: 0;font-weight: bold; text-align: right; width: 100%; box-sizing: border-box;">{{ new Date(Date.now()).toLocaleString() }}</p>
		<hr style="width: 5em; float: left; border: 1px solid rgb(192, 188, 188);">
		<div class="bill-infor">
			<p style="margin: 0; font-weight: bold; margin-bottom: 10px; width: 100%; text-align: left; display: inline-block;">BILLING TO:</p>
			<div class="name">
				<p>Client's Name: </p>
				<span>{{ this.productOrder.user.firstname }} {{ this.productOrder.user.lastname }}</span>
			</div>
			<div class="phone">
				<p>Phone: </p>
				<span>{{ this.productOrder.user.phone }}</span>
			</div>
			<div class="address">
				<p>Address: </p>
				<span>{{ this.productOrder.user.address }}</span>
			</div>
		</div>
		<table>
			<tr>
				<th style="width: 20%; box-sizing: border-box; padding-left: 30px;">Image</th>
				<th style="width: 30%;">Product Name</th>
				<th style="width: 20%;">Price</th>
				<th style="width: 10%;">Qty</th>
				<th style="width: 20%;">Total</th>
			</tr>
			<tr style="border-bottom: 1px solid rgb(192, 188, 188); height: 3em;"
				v-for="(orderItem, index) in this.productOrder.orderItem" :key="index">
				<td style="display: flex; align-items: center; height: 3em; box-sizing: border-box; padding-left: 30px;">
					<figure>
						<img :src="this.url + orderItem.products.productImage" />
					</figure>
				</td>
				<td>{{ orderItem.products.productName }}</td>
				<td>${{ orderItem.products.productPrice }}</td>
				<td>x{{ orderItem.quantity }}</td>
				<td>${{ orderItem.price }}</td>
			</tr>
		</table>
		<div class="total">
			<span>TOTAL:</span>
			<span> ${{ this.productOrder.totalPrice }}</span>
		</div>
		<div class="infor">
      <span><font-awesome-icon class="information-phone-icon" icon="fa-solid fa-phone" /> 0763.096.798</span>			
      <span><font-awesome-icon class="information-email-icon" icon="fa-solid fa-envelope" /> vannamka6626@gmail.com</span>
		</div>
	</div>
</template>
<script>
import store from '@/store';
import { library } from "@fortawesome/fontawesome-svg-core";
import { faEnvelope, faPhone } from "@fortawesome/free-solid-svg-icons";
library.add(faEnvelope, faPhone);
export default {
	props: {
		productOrder: {
			type: Object,
			require: true
		}
	},
	data() {
		return {
			url: store.state.product.url,
		}
	},
}
</script>
<style scoped>
.bill-item {
	height: auto;
	min-height: 20rem;
	background-color: #fff;
	box-sizing: border-box;
	padding-top: 1em;
}

.bill-infor {
	box-sizing: border-box;
	padding-left: 2em;
	margin-bottom: 1em;
}

.name,
.phone,
.address {
	display: flex;
}

.name p,
.phone p,
.address p {
	margin: 0;
	width: 8em;
}

table {
	border-collapse: collapse;
	width: 100%;
}
th {
	text-align: left;
	color: #fff;
	background-color: #00000A;
	text-transform: uppercase;
	height: 2em;
}

figure {
	display: flex;
	align-items: center;
	box-sizing: border-box;
	overflow: hidden;
	width: 45px;
	margin: 0px;
	padding: 0px;
	height: 100%;
}

img {
	transition: .5s;
	width: 100%;
	cursor: pointer;
}

img:hover {
	transform: scale(1.2);
}

.total {
	display: flex;
	justify-content: right;
	color: red;
	width: 100%;
	font-weight: bold;
	box-sizing: border-box;
	padding-right: 10%;
	margin: 2em 0;
}
.infor {
	display: flex;
	justify-content: center;
	align-items: center;
	gap: 2em;
	background-color: #00000A;
	color: #fff;
	width: 100%;
	height: 3em;
}
</style>

