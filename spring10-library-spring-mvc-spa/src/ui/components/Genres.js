import React from 'react'
import {Helmet} from "react-helmet/es/Helmet";
import {Header} from "./Header";



export default class Genres extends React.Component {

    constructor() {
        super();
        this.state = {genres: []};
    }

    componentDidMount() {
        fetch('/api/v1/genres')
            .then(response => response.json())
            .then(genres => this.setState({genres}));
    }


    render() {

        return (
            <React.Fragment>
                <div className={Genres}>
                    <Helmet>
                        <meta charSet="utf-8" />
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
                            <th>ID</th>
                            <th>Название жанра</th>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            this.state.genres.map((genre, i) => (
                                <tr key={i}>
                                    <td>{genre.id}</td>
                                    <td>{genre.genreTitle}</td>
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