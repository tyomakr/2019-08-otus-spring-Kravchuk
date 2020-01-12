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
                    <span><b>Spring Boot Actuator</b></span>
                    <ul>
                        <li>Использовать метрики, healthchecks и logfile к приложению</li>
                        <li>Реализовать собственную метрику, InfoContributor, HealthIndicator или Endpoint</li>
                        <li>Опционально: переписать приложение на HATEOAS принципах</li>
                    </ul>
                </div>
            </React.Fragment>
        )
    }
}
