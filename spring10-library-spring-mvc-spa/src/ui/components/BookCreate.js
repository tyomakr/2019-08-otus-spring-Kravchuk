import React from 'react'
import {Helmet} from "react-helmet/es/Helmet";
import axios from 'axios'
import {Header} from "./Header";


function getDefaultState() {
    return {
        title: '',
        authors: '',
        genres: ''
    };
}


export default class BookCreate extends React.Component {

    constructor(){
        super()

        this.state = {
            title: "",
            authors: "",
            genres: ""
        }
    }

    dataChange(event) {
        this.setState({
            [event.target.name]:event.target.value
        })
    }

    postData(event) {
        event.preventDefault()

        const title = this.state.title;
        const authors = this.state.authors;
        const genres = this.state.genres;

        this.setState({
            loading: true
        });

        const data = {
            title,
            authors,
            genres
        };

        axios.post('http://localhost:8888/api/v1/books/create', data)
            .then(response => {
                console.log(response)
                this.setState({
                    loading: false,
                    message: response.data
                })
            })
            .catch(err => {
                console.log(err)
                this.setState({
                    loading: false
                })
            })

        this.setState(getDefaultState());

    }

    loadOrShowMsg() {
        if (this.state.loading) {
            return <p>loading....</p>
        } else {
            return <p>{this.state.message}</p>
        }
    }


    render() {

        return (
            <React.Fragment>
                <div>
                    <Helmet>
                        <meta charSet="utf-8" />
                        <title>React Spring Library - Добавить книгу в библиотеку</title>
                    </Helmet>
                </div>
                <Header history={this.props.history}/>

                <div className="container mt-2 ml-2">
                    <h2>Добавление книги</h2>
                </div>
                <div className="container">
                    <form onSubmit={this.postData.bind(this)}>
                        <div className="form-group">
                            <input className="form-control" type="text" name="title" aria-describedby="titleHelp"
                                   value={this.props.title} onChange={this.dataChange.bind(this)} required />
                                <small className="form-text text-muted" id="titleHelp">Пожалуйста введите название книги</small>
                        </div>
                        <div className="form-group">
                            <input className="form-control" type="text" name="authors" aria-describedby="authorHelp"
                                   value={this.props.authors} onChange={this.dataChange.bind(this)} required />
                                <small className="form-text text-muted" id="authorHelp">Пожалуйста введите имя одного автора</small>
                        </div>
                        <div className="form-group">
                        <input className="form-control" type="text" name="genres" aria-describedby="genreHelp"
                               value={this.props.genres} onChange={this.dataChange.bind(this)} required />
                                <small className="form-text text-muted" id="genreHelp">Пожалуйста введите название одного жанра</small>
                        </div>
                        <div className="float-right my-sm 1">
                            <button className="btn btn-primary" type="submit">Добавить книгу</button>
                        </div>
                    </form>
                    {this.loadOrShowMsg()}
                </div>
            </React.Fragment>
        )
    }
}