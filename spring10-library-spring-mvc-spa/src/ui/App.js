import React from 'react';
import './css/style.css';
import {Helmet} from "react-helmet/es/Helmet";
import {Header} from "./fragments/Header";


export default class App extends React.Component {

    render() {

        return (
            <React.Fragment>
                <div className={App}>
                    <Helmet>
                        <meta charSet="utf-8"/>
                        <title>React Spring Library - Главная страница</title>
                    </Helmet>
                </div>
                <Header history={this.props.history}/>

                <div className="container mt-2 ml-2">
                    <span><b>Современные приложения на Spring MVC</b></span>
                    <ul>
                        <li>Переписать приложение с использованием AJAX и REST-контроллеров</li>
                        <li>Переписать приложение с классических View на AJAX архитектуру и REST-контроллеры.</li>
                        <li>Опционально: Сделать SPA приложение на любом из Web-фреймоврков</li>
                    </ul>
                </div>
            </React.Fragment>
        )
    }

}

