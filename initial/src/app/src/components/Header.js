import React, { Component } from "react";
import { Navbar, Nav, NavItem , FormGroup , Button , FormControl ,Tooltip,OverlayTrigger} from "react-bootstrap";
//import logo from "../../assets/logo.png";
import { connect } from "react-redux";
import { searchProduct, getProducts} from "../actions/productActions";
class NavbarComponent extends Component {
    constructor(props) {
        super(props);
        this.onFailure = this.onFailure.bind(this)

    }
    state={searchStr:''}
    onSearch(e) {
        e.preventDefault();
       this.props.searchProduct(this.state.searchStr)
    }
    onFailure(msg) {

       }
    clearSearch = () =>{
        this.setState({searchStr : ''});
        this.props.getProducts(this.onFailure);
    }
    render() {
        const tooltip = (
            <Tooltip id="tooltip">
                Search the name of Product!
            </Tooltip>
        );
        return (
            <Navbar className="navbar navbar-expand-lg navbar-light bg-light  ">
                <Navbar.Header>
                    <Navbar.Brand>
                        <img style={{ paddingTop: '17px',paddingLeft:'10px',height:'75px' }}
                             className="navbar-logo"
                             src="/assets/img/logo.png"/>
                    </Navbar.Brand>
                </Navbar.Header>
                <Nav>
                    <NavItem eventKey={1}>
                        <h4>Welcome to My Retails Shop</h4>
                    </NavItem>

                </Nav>
                <Navbar.Collapse>
                    <Navbar.Form pullRight>
                        <FormGroup>
                            {this.state.searchStr && <a onClick={this.clearSearch}  >Clear Search! </a>}
                                <FormControl
                                    value={this.state.searchStr}
                                    onChange = {(param)=> {console.log ('search for .. ',param.target.value);this.setState({searchStr:param.target.value})}}
                                    type="text" placeholder="Search"/>
                        </FormGroup>
                    <OverlayTrigger placement="right" overlay={tooltip}>
                        <Button  onClick={this.onSearch.bind(this)} style={{margin:"5px"}} type="submit" className="btn-info">Submit</Button>
                    </OverlayTrigger>
                    </Navbar.Form>
                </Navbar.Collapse>
            </Navbar>
        );
    }
}

const mapStateToProps = state => ({
    products: state.reducer.products
});
export default connect(
    mapStateToProps,
    {searchProduct ,getProducts}
)(NavbarComponent);
