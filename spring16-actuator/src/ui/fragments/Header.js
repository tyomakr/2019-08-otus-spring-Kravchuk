import React from 'react'
import {Link} from 'react-router-dom'
import { Navbar, Nav, Collapse} from 'bootstrap-4-react';

export class Header extends React.Component {

    render() {
        return (
            <header>
                <Navbar expand="md" dark bg="dark">
                    <Navbar.Brand href="#">
                        Spring React Library
                    </Navbar.Brand>
                    <Navbar.Toggler target="#navbarSupportedContent" />
                    <Collapse navbar id="navbarSupportedContent">
                        <Navbar.Nav mr="auto">
                            <Nav.Item active>
                                <Link to={"/"}><Nav.Link>Главная</Nav.Link></Link>
                            </Nav.Item>
                            <Nav.Item>
                                <Link to={"/Books"}><Nav.Link>Книги</Nav.Link></Link>
                            </Nav.Item>
                            <Nav.Item>
                                <Link to={"/Authors"}><Nav.Link>Авторы</Nav.Link></Link>
                            </Nav.Item>
                            <Nav.Item>
                                <Link to={"/Genres"}><Nav.Link>Жанры</Nav.Link></Link>
                            </Nav.Item>
                            <Nav.Item>
                                <a href={"/monitor/"}><Nav.Link>Actuator</Nav.Link></a>
                            </Nav.Item>
                            <Nav.Item>
                                <a href={"/monitor/info"}><Nav.Link>Info Contributor (custom)</Nav.Link></a>
                            </Nav.Item>
                        </Navbar.Nav>
                    </Collapse>
                </Navbar>
            </header>
        )
    }
}