import React, { Component } from "react";
import { Navbar, Nav, NavItem , FormGroup , Button , FormControl ,Tooltip,OverlayTrigger} from "react-bootstrap";
//import logo from "../../assets/logo.png";
import { connect } from "react-redux";
class NavbarComponent extends Component {
    componentWillMount() {
        console.log("navbar props", this.props.products);
        console.log("navbar state", this.state);
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
                            <OverlayTrigger placement="left" overlay={tooltip}>
                                <FormControl type="text" placeholder="Search"/>
                            </OverlayTrigger>
                        </FormGroup>

                        <Button style={{margin:"5px"}} type="submit" className="btn-info">Submit</Button>
                    </Navbar.Form>
                </Navbar.Collapse>
            </Navbar>
        );
    }
}

//export default NavbarComponent;
const mapStateToProps = state => ({
    total: state.reducer.total
});
export default connect(
    mapStateToProps,
    {}
)(NavbarComponent);
