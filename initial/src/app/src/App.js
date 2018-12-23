import React, { Component } from "react";
import "./App.css";
import CreateProduct from "./components/CreateEditProduct";
import NavbarComponent from "./components/Header";
import Products from "./components/ProductsList";
// import SearchProduct from "./components/search";
import { Provider } from "react-redux";
import store from "./store.js";
class App extends Component {
  render() {
    return (
        <Provider store={store}>
          <div  >
            <NavbarComponent total={store.total} error={store.error} />
            {/* <TabComponent> */}
            <CreateProduct />
            {/* <SearchProduct /> */}
            {/* </TabComponent> */}
            <Products products={store.products} />
          </div>
        </Provider>
    );
  }
}

export default App;
