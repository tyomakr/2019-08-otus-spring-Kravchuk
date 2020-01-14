import React from 'react'
import {Helmet} from "react-helmet/es/Helmet";
import {Header} from "../fragments/Header";
import Client from "../service/Client";


export default class Genres extends React.Component {

    constructor() {
        super();
        this.state = {genres: []};
    }

    componentDidMount() {
        Client({method: 'GET', path: '/api/v1/genres'}).done(response => {
            this.setState({genres: response.entity._embedded.genres});
        });
    }


    render() {

        return (
            <React.Fragment>
                <div className={Genres}>
                    <Helmet>
                        <meta charSet="utf-8"/>
                        <title>React Spring Library - Жанры</title>
                    </Helmet>
                </div>
                <Header history={this.props.history}/>

                <div className="container mt-2 ml-2">
                    <h2>Жанры</h2>
                </div>
                <div className="container mt-3 ml-3">
                    <table className="table">
                        <thead>
                        <tr>
                            <th>Номер</th>
                            <th>Название жанра</th>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            this.state.genres.map((genre, i) => (
                                <tr key={i}>
                                    <td>{i}</td>
                                    <td>{genre.genreTitle.toString()}</td>
                                </tr>
                            ))
                        }
                        </tbody>
                    </table>
                </div>
            </React.Fragment>
        )
    }
}